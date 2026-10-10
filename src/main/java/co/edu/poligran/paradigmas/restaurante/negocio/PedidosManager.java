package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.*;
import java.util.List;

/** Gestiona pedidos, detalles, estados, entrega y cancelación. */
public class PedidosManager {
    private final DatosRestaurante datos;
    public PedidosManager(DatosRestaurante datos) { this.datos = datos; }
    public Pedido crear(int idMesa, int idMesero, TipoPedido tipo) {
        Usuario mesero = datos.usuarios.buscarPorId(idMesero);
        if (mesero == null || mesero.getRol() != Rol.MESERO) throw new IllegalArgumentException("Debe indicar un mesero registrado");
        if (tipo == TipoPedido.SALON) {
            Mesa mesa = datos.mesas.buscarPorId(idMesa);
            if (mesa == null) throw new IllegalArgumentException("La mesa no existe");
            if (mesa.isOcupada()) throw new IllegalStateException("La mesa está ocupada");
            mesa.setOcupada(true); datos.mesas.actualizar(mesa);
        }
        Pedido pedido = datos.pedidos.crear(new Pedido(idMesa, idMesero, tipo));
        datos.historial.crear(new HistorialEstado(pedido.getId(), EstadoPedido.PENDIENTE));
        return pedido;
    }
    public DetallePedido agregarProducto(int idPedido, int idProducto, int cantidad) {
        Pedido pedido=datos.pedidos.buscarPorId(idPedido); Producto producto=datos.productos.buscarPorId(idProducto);
        if(pedido==null) throw new IllegalArgumentException("El pedido no existe");
        if(producto==null || !producto.isDisponible()) throw new IllegalArgumentException("El producto no está disponible");
        if(cantidad<=0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        DetallePedido detalle=new DetallePedido(idProducto,cantidad,producto.getPrecio()); detalle.setIdPedido(idPedido); return datos.detalles.crear(detalle);
    }
    public double calcularTotal(int idPedido) { Pedido pedido=obtener(idPedido); return pedido.calcularTotal(datos.detalles.listarPorPedido(idPedido)); }
    public Pedido obtener(int id) { Pedido pedido=datos.pedidos.buscarPorId(id); if(pedido==null) throw new IllegalArgumentException("El pedido no existe"); return pedido; }
    public List<Pedido> listar() { return datos.pedidos.listar(); }
    public List<Pedido> verPendientes() { return datos.pedidos.listarPorEstado(EstadoPedido.PENDIENTE); }
    public List<Pedido> verListos() { return datos.pedidos.listarPorEstado(EstadoPedido.LISTO); }
    public List<DetallePedido> verDetalles(int idPedido) { return datos.detalles.listarPorPedido(idPedido); }
    public List<HistorialEstado> verHistorial(int idPedido) { return datos.historial.listarPorPedido(idPedido); }
    public boolean cambiarEstado(int idPedido, EstadoPedido nuevoEstado) {
        Pedido pedido=obtener(idPedido);
        if (!transicionValida(pedido.getEstado(), nuevoEstado)) throw new IllegalStateException("Transición de estado no permitida");
        pedido.setEstado(nuevoEstado); datos.pedidos.actualizar(pedido); datos.historial.crear(new HistorialEstado(idPedido,nuevoEstado));
        if(nuevoEstado==EstadoPedido.ENTREGADO || nuevoEstado==EstadoPedido.CANCELADO) liberarMesa(pedido);
        return true;
    }
    /** Solo permite cancelar en PENDIENTE o EN_PREPARACION, como exige el documento. */
    public boolean cancelar(int idPedido) {
        Pedido pedido=obtener(idPedido);
        if(pedido.getEstado()!=EstadoPedido.PENDIENTE && pedido.getEstado()!=EstadoPedido.EN_PREPARACION) throw new IllegalStateException("Solo se puede cancelar un pedido pendiente o en preparación");
        return cambiarEstado(idPedido, EstadoPedido.CANCELADO);
    }
    public boolean asignarDomiciliario(int idPedido, int idDomiciliario) {
        Pedido pedido=obtener(idPedido); Usuario usuario=datos.usuarios.buscarPorId(idDomiciliario);
        if(!(usuario instanceof Domiciliario) || !((Domiciliario)usuario).isDisponible()) throw new IllegalArgumentException("Domiciliario no disponible");
        if(pedido.getTipo()!=TipoPedido.DOMICILIO || pedido.getEstado()!=EstadoPedido.LISTO) throw new IllegalStateException("Solo se asignan pedidos a domicilio listos");
        Domiciliario domiciliario=(Domiciliario)usuario; pedido.setIdDomiciliario(idDomiciliario); domiciliario.setDisponible(false); datos.pedidos.actualizar(pedido);
        return cambiarEstado(idPedido, EstadoPedido.EN_CAMINO);
    }
    public boolean entregarDomicilio(int idPedido) { Pedido pedido=obtener(idPedido); if(pedido.getEstado()!=EstadoPedido.EN_CAMINO) throw new IllegalStateException("El pedido no está en camino"); Usuario u=datos.usuarios.buscarPorId(pedido.getIdDomiciliario()); if(u instanceof Domiciliario)((Domiciliario)u).setDisponible(true); return cambiarEstado(idPedido,EstadoPedido.ENTREGADO); }
    public boolean entregarSalon(int idPedido) { Pedido pedido=obtener(idPedido); if(pedido.getTipo()!=TipoPedido.SALON || pedido.getEstado()!=EstadoPedido.LISTO) throw new IllegalStateException("El pedido de salón debe estar listo"); return cambiarEstado(idPedido,EstadoPedido.ENTREGADO); }
    private boolean transicionValida(EstadoPedido actual,EstadoPedido nuevo) {
        if(nuevo==EstadoPedido.CANCELADO) return actual==EstadoPedido.PENDIENTE || actual==EstadoPedido.EN_PREPARACION;
        if(actual==EstadoPedido.PENDIENTE) return nuevo==EstadoPedido.EN_PREPARACION;
        if(actual==EstadoPedido.EN_PREPARACION) return nuevo==EstadoPedido.LISTO;
        if(actual==EstadoPedido.LISTO) return nuevo==EstadoPedido.EN_CAMINO || nuevo==EstadoPedido.ENTREGADO;
        return actual==EstadoPedido.EN_CAMINO && nuevo==EstadoPedido.ENTREGADO;
    }
    private void liberarMesa(Pedido pedido) { if(pedido.getTipo()==TipoPedido.SALON){ Mesa mesa=datos.mesas.buscarPorId(pedido.getIdMesa()); if(mesa!=null){mesa.setOcupada(false);datos.mesas.actualizar(mesa);} } }
}
