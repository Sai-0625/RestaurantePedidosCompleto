package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.dao.*;
import co.edu.poligran.paradigmas.restaurante.modelo.*;
import java.util.List;

/** Capa de negocio: centraliza DAO y valida las relaciones del sistema. */
public class RestauranteManager {
    private final UsuarioDAO usuarios = new UsuarioDAOImpl();
    private final CategoriaDAO categorias = new CategoriaDAOImpl();
    private final ProductoDAO productos = new ProductoDAOImpl();
    private final MesaDAO mesas = new MesaDAOImpl();
    private final PedidoDAO pedidos = new PedidoDAOImpl();
    private final DetallePedidoDAO detalles = new DetallePedidoDAOImpl();
    private final HistorialEstadoDAO historial = new HistorialEstadoDAOImpl();

    // Usuarios
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarios.buscarPorUsuario(usuario.getUsuario()) != null) throw new IllegalArgumentException("El nombre de usuario ya existe");
        return usuarios.crear(usuario);
    }
    public List<Usuario> listarUsuarios() { return usuarios.listar(); }
    public Usuario buscarUsuario(int id) { return usuarios.buscarPorId(id); }
    public boolean actualizarUsuario(Usuario usuario) { return usuarios.actualizar(usuario) != null; }
    public boolean eliminarUsuario(int id) { return usuarios.eliminar(id); }

    // Categorías
    public Categoria crearCategoria(Categoria categoria) { return categorias.crear(categoria); }
    public List<Categoria> listarCategorias() { return categorias.listar(); }
    public boolean actualizarCategoria(Categoria categoria) { return categorias.actualizar(categoria) != null; }
    public boolean eliminarCategoria(int idCategoria) {
        if (!productos.listarPorCategoria(idCategoria).isEmpty()) throw new IllegalStateException("No se puede eliminar una categoría con productos asociados");
        return categorias.eliminar(idCategoria);
    }

    // Productos
    public Producto crearProducto(Producto producto) {
        validarCategoria(producto.getIdCategoria());
        if (producto.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        return productos.crear(producto);
    }
    public List<Producto> listarProductos() { return productos.listar(); }
    public List<Producto> listarProductosPorCategoria(int idCategoria) { return productos.listarPorCategoria(idCategoria); }
    public boolean actualizarProducto(Producto producto) {
        validarCategoria(producto.getIdCategoria());
        if (producto.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        return productos.actualizar(producto) != null;
    }
    public boolean eliminarProducto(int idProducto) { return productos.eliminar(idProducto); }

    // Mesas
    public Mesa crearMesa(Mesa mesa) {
        if (mesas.buscarPorNumero(mesa.getNumero()) != null) throw new IllegalArgumentException("Ya existe una mesa con ese número");
        if (mesa.getCapacidad() <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        return mesas.crear(mesa);
    }
    public List<Mesa> listarMesas() { return mesas.listar(); }
    public boolean actualizarMesa(Mesa mesa) { return mesas.actualizar(mesa) != null; }
    public boolean eliminarMesa(int idMesa) {
        Mesa mesa = mesas.buscarPorId(idMesa);
        if (mesa != null && mesa.isOcupada()) throw new IllegalStateException("No se puede eliminar una mesa ocupada");
        return mesas.eliminar(idMesa);
    }

    // Pedidos, detalles e historial
    public Pedido crearPedido(int idMesa, int idMesero) {
        Mesa mesa = mesas.buscarPorId(idMesa);
        Usuario mesero = usuarios.buscarPorId(idMesero);
        if (mesa == null) throw new IllegalArgumentException("La mesa no existe");
        if (mesero == null || mesero.getRol() != Rol.MESERO) throw new IllegalArgumentException("El usuario indicado no es un mesero");
        if (mesa.isOcupada()) throw new IllegalStateException("La mesa ya está ocupada");
        mesa.setOcupada(true);
        mesas.actualizar(mesa);
        Pedido pedido = pedidos.crear(new Pedido(idMesa, idMesero));
        historial.crear(new HistorialEstado(pedido.getId(), EstadoPedido.PENDIENTE));
        return pedido;
    }

    /** Usa el precio actual del producto; la interfaz no puede modificarlo. */
    public DetallePedido agregarProducto(int idPedido, int idProducto, int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        Pedido pedido = pedidos.buscarPorId(idPedido);
        Producto producto = productos.buscarPorId(idProducto);
        if (pedido == null) throw new IllegalArgumentException("Pedido inexistente");
        if (producto == null || !producto.isDisponible()) throw new IllegalArgumentException("Producto no disponible");
        DetallePedido detalle = new DetallePedido(idProducto, cantidad, producto.getPrecio());
        detalle.setIdPedido(idPedido);
        return detalles.crear(detalle);
    }

    public List<Pedido> listarPedidos() { return pedidos.listar(); }
    public List<Pedido> verPedidosPendientes() { return pedidos.listarPorEstado(EstadoPedido.PENDIENTE); }
    public List<DetallePedido> verDetallePedido(int idPedido) { return detalles.listarPorPedido(idPedido); }
    public List<HistorialEstado> verHistorial(int idPedido) { return historial.listarPorPedido(idPedido); }

    public boolean cambiarEstado(int idPedido, EstadoPedido estado) {
        Pedido pedido = pedidos.buscarPorId(idPedido);
        if (pedido == null) return false;
        pedido.setEstado(estado);
        pedidos.actualizar(pedido);
        historial.crear(new HistorialEstado(idPedido, estado));
        if (estado == EstadoPedido.ENTREGADO || estado == EstadoPedido.CANCELADO) liberarMesa(pedido.getIdMesa());
        return true;
    }

    public boolean asignarDomiciliario(int idPedido, Domiciliario domiciliario) {
        Pedido pedido = pedidos.buscarPorId(idPedido);
        if (pedido == null || domiciliario == null || !domiciliario.isDisponible() || pedido.getEstado() != EstadoPedido.LISTO) return false;
        pedido.setIdDomiciliario(domiciliario.getId());
        domiciliario.setDisponible(false);
        pedidos.actualizar(pedido);
        return cambiarEstado(idPedido, EstadoPedido.EN_CAMINO);
    }

    private void validarCategoria(int idCategoria) {
        if (categorias.buscarPorId(idCategoria) == null) throw new IllegalArgumentException("La categoría no existe");
    }
    private void liberarMesa(int idMesa) {
        Mesa mesa = mesas.buscarPorId(idMesa);
        if (mesa != null) { mesa.setOcupada(false); mesas.actualizar(mesa); }
    }
}
