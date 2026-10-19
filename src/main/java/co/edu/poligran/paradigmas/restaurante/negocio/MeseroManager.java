package co.edu.poligran.paradigmas.restaurante.negocio;

import java.util.ArrayList;
import java.util.List;

import co.edu.poligran.paradigmas.restaurante.modelo.DetallePedido;
import co.edu.poligran.paradigmas.restaurante.modelo.EstadoPedido;
import co.edu.poligran.paradigmas.restaurante.modelo.HistorialEstado;
import co.edu.poligran.paradigmas.restaurante.modelo.Mesa;
import co.edu.poligran.paradigmas.restaurante.modelo.Pedido;
import co.edu.poligran.paradigmas.restaurante.modelo.Producto;
import co.edu.poligran.paradigmas.restaurante.modelo.Rol;
import co.edu.poligran.paradigmas.restaurante.modelo.TipoPedido;
import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;


 
public class MeseroManager {

    private final DatosRestaurante datos;

    public MeseroManager(DatosRestaurante datos) {
        this.datos = datos;
    }


    /**
     *
     * @param idMesero id del usuario con rol mesero que toma el pedido
     * @param tipo     SALON, PARA_LLEVAR o DOMICILIO
     * @param idMesa   id de la mesa solo se usa si el tipo es salon en otro caso se ignora
     */
    public Pedido crearPedido(int idMesero, TipoPedido tipo, int idMesa) {
        Usuario mesero = datos.usuarios.buscarPorId(idMesero);
        if (mesero == null || mesero.getRol() != Rol.MESERO) {
            throw new IllegalArgumentException("Debe indicar un mesero registrado");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de pedido es obligatorio");
        }
        int mesaDelPedido = 0;
        if (tipo == TipoPedido.SALON) {
            Mesa mesa = datos.mesas.buscarPorId(idMesa);
            if (mesa == null) {
                throw new IllegalArgumentException("La mesa no existe");
            }
            if (mesa.isOcupada()) {
                throw new IllegalStateException("La mesa esta ocupada");
            }
            mesa.setOcupada(true);
            datos.mesas.actualizar(mesa);
            mesaDelPedido = idMesa;
        }
        Pedido pedido = datos.pedidos.crear(new Pedido(mesaDelPedido, idMesero, tipo));
        datos.historial.crear(new HistorialEstado(pedido.getId(), EstadoPedido.PENDIENTE));
        return pedido;
    }

    
    public DetallePedido agregarProducto(int idPedido, int idProducto, int cantidad) {
        Pedido pedido = pedidoModificable(idPedido);
        Producto producto = datos.productos.buscarPorId(idProducto);
        if (producto == null || !producto.isDisponible()) {
            throw new IllegalArgumentException("El producto no esta disponible");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        DetallePedido existente = buscarDetalle(pedido.getId(), idProducto);
        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            return datos.detalles.actualizar(existente);
        }
        DetallePedido detalle = new DetallePedido(idProducto, cantidad, producto.getPrecio());
        detalle.setIdPedido(pedido.getId());
        return datos.detalles.crear(detalle);
    }

    
    
    public boolean quitarProducto(int idPedido, int idProducto) {
        Pedido pedido = pedidoModificable(idPedido);
        DetallePedido detalle = buscarDetalle(pedido.getId(), idProducto);
        if (detalle == null) {
            throw new IllegalArgumentException("Ese producto no esta en el pedido");
        }
        return datos.detalles.eliminar(detalle.getId());
    }

    
    
    public Pedido asignarMesa(int idPedido, int idMesaNueva) {
        Pedido pedido = pedidoModificable(idPedido);
        if (pedido.getTipo() != TipoPedido.SALON) {
            throw new IllegalStateException("Solo los pedidos de salon tienen mesa");
        }
        Mesa nueva = datos.mesas.buscarPorId(idMesaNueva);
        if (nueva == null) {
            throw new IllegalArgumentException("La mesa no existe");
        }
        if (nueva.getId() == pedido.getIdMesa()) {
            return pedido; // ya tenia esa mesa, no hay nada que cambiar
        }
        if (nueva.isOcupada()) {
            throw new IllegalStateException("La mesa esta ocupada");
        }
        liberarMesa(pedido.getIdMesa());
        nueva.setOcupada(true);
        datos.mesas.actualizar(nueva);
        pedido.setIdMesa(idMesaNueva);
        return datos.pedidos.actualizar(pedido);
    }

    
    public Pedido marcarComoEntregado(int idPedido) {
        Pedido pedido = obtenerPedido(idPedido);
        if (pedido.getTipo() == TipoPedido.DOMICILIO) {
            throw new IllegalStateException("Los pedidos a domicilio los entrega el domiciliario");
        }
        if (pedido.getEstado() != EstadoPedido.LISTO) {
            throw new IllegalStateException("El pedido debe estar LISTO para entregarse");
        }
        pedido.setEstado(EstadoPedido.ENTREGADO);
        datos.pedidos.actualizar(pedido);
        datos.historial.crear(new HistorialEstado(idPedido, EstadoPedido.ENTREGADO));
        if (pedido.getTipo() == TipoPedido.SALON) {
            liberarMesa(pedido.getIdMesa());
        }
        return pedido;
    }

    // CRUD leer, actualizar y eliminar

    /** Leer un pedido por id (lanza error si no existe). */
    public Pedido obtenerPedido(int idPedido) {
        Pedido pedido = datos.pedidos.buscarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no existe");
        }
        return pedido;
    }

    /** Leer todos los pedidos. */
    public List<Pedido> listarPedidos() {
        return datos.pedidos.listar();
    }

    /** Leer solo los pedidos tomados por un mesero. */
    public List<Pedido> listarMisPedidos(int idMesero) {
        List<Pedido> mios = new ArrayList<>();
        for (Pedido pedido : datos.pedidos.listar()) {
            if (pedido.getIdMesero() == idMesero) {
                mios.add(pedido);
            }
        }
        return mios;
    }

    /** Leer las lineas productos y cantidades de un pedido. */
    public List<DetallePedido> verDetalle(int idPedido) {
        obtenerPedido(idPedido);
        return datos.detalles.listarPorPedido(idPedido);
    }

    /** Total del pedido = suma de cantidad x precio unitario de cada linea. */
    public double calcularTotal(int idPedido) {
        return obtenerPedido(idPedido).calcularTotal(datos.detalles.listarPorPedido(idPedido));
    }

    /** Leer el historial de estados con su fecha de un pedido. */
    public List<HistorialEstado> verHistorial(int idPedido) {
        obtenerPedido(idPedido);
        return datos.historial.listarPorPedido(idPedido);
    }

    /** Actualizar la cantidad de un producto que ya esta en el pedido solo si esta PENDIENTE. */
    public DetallePedido cambiarCantidad(int idPedido, int idProducto, int nuevaCantidad) {
        Pedido pedido = pedidoModificable(idPedido);
        if (nuevaCantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero (para quitarlo use quitarProducto)");
        }
        DetallePedido detalle = buscarDetalle(pedido.getId(), idProducto);
        if (detalle == null) {
            throw new IllegalArgumentException("Ese producto no esta en el pedido");
        }
        detalle.setCantidad(nuevaCantidad);
        return datos.detalles.actualizar(detalle);
    }

    /**
     * Borra en cascada sus detalles y su historial, y libera la mesa para anular un pedido ya en
     * preparacion se usa CANCELAR (PedidosManager) que conserva el rastro
     */
    public boolean eliminarPedido(int idPedido) {
        Pedido pedido = pedidoModificable(idPedido);
        for (DetallePedido detalle : new ArrayList<>(datos.detalles.listarPorPedido(idPedido))) {
            datos.detalles.eliminar(detalle.getId());
        }
        for (HistorialEstado cambio : new ArrayList<>(datos.historial.listarPorPedido(idPedido))) {
            datos.historial.eliminar(cambio.getId());
        }
        if (pedido.getTipo() == TipoPedido.SALON) {
            liberarMesa(pedido.getIdMesa());
        }
        return datos.pedidos.eliminar(idPedido);
    }

    // Metodos auxiliares privados

    /** Devuelve el pedido solo si existe y sigue en PENDIENTE si no lanza error */
    private Pedido pedidoModificable(int idPedido) {
        Pedido pedido = obtenerPedido(idPedido);
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("Solo se puede modificar un pedido PENDIENTE");
        }
        return pedido;
    }

    /** Busca la linea de un producto dentro de un pedido null si no esta */
    private DetallePedido buscarDetalle(int idPedido, int idProducto) {
        for (DetallePedido detalle : datos.detalles.listarPorPedido(idPedido)) {
            if (detalle.getIdProducto() == idProducto) {
                return detalle;
            }
        }
        return null;
    }

    private void liberarMesa(int idMesa) {
        Mesa mesa = datos.mesas.buscarPorId(idMesa);
        if (mesa != null) {
            mesa.setOcupada(false);
            datos.mesas.actualizar(mesa);
        }
    }
}


