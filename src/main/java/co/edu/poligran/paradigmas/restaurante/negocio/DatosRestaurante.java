package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.dao.*;

/** Contenedor único de DAO en memoria compartido por todos los managers. */
public class DatosRestaurante {
    public final UsuarioDAO usuarios = new UsuarioDAOImpl();
    public final CategoriaDAO categorias = new CategoriaDAOImpl();
    public final ProductoDAO productos = new ProductoDAOImpl();
    public final MesaDAO mesas = new MesaDAOImpl();
    public final PedidoDAO pedidos = new PedidoDAOImpl();
    public final DetallePedidoDAO detalles = new DetallePedidoDAOImpl();
    public final HistorialEstadoDAO historial = new HistorialEstadoDAOImpl();
}
