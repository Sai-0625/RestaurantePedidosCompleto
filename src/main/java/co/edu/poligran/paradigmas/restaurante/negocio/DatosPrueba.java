package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.*;

/** Escenario local y reproducible para probar todas las funcionalidades. */
public final class DatosPrueba {
    private DatosPrueba() { }

    public static void cargar(UsuariosManager usuarios, CatalogoManager catalogo, MesasManager mesas, PedidosManager pedidos) {
        if (!catalogo.listarProductos().isEmpty()) return;

        Categoria entradas = categoria(1, "Entradas", "Aperitivos para comenzar");
        Categoria principales = categoria(2, "Platos fuertes", "Platos principales");
        Categoria bebidas = categoria(3, "Bebidas", "Bebidas frías y calientes");
        catalogo.crearCategoria(entradas); catalogo.crearCategoria(principales); catalogo.crearCategoria(bebidas);

        catalogo.crearProducto(producto(1, "Empanadas", 6000, 1));
        catalogo.crearProducto(producto(2, "Nachos con queso", 12000, 1));
        catalogo.crearProducto(producto(3, "Hamburguesa clásica", 22000, 2));
        catalogo.crearProducto(producto(4, "Pizza personal", 25000, 2));
        catalogo.crearProducto(producto(5, "Pasta carbonara", 28000, 2));
        catalogo.crearProducto(producto(6, "Limonada natural", 7000, 3));
        catalogo.crearProducto(producto(7, "Gaseosa", 5000, 3));
        catalogo.crearProducto(producto(8, "Agua", 4000, 3));
        catalogo.crearProducto(producto(9, "Café", 4500, 3));

        mesas.crear(mesa(1, 1, 4)); mesas.crear(mesa(2, 2, 2)); mesas.crear(mesa(3, 3, 6));
        mesas.crear(mesa(4, 4, 4)); mesas.crear(mesa(5, 5, 8));

        usuarios.registrar(usuarioMesero(1, "Ana Torres", "mesero1"));
        usuarios.registrar(usuarioMesero(2, "David Rojas", "mesero2"));
        usuarios.registrar(usuarioCocina(3, "Luis Gómez", "cocina"));
        usuarios.registrar(usuarioDomiciliario(4, "Sofía Díaz", "domiciliario", "3001234567"));
        usuarios.registrar(usuarioAdmin(5, "Carlos Ruiz", "admin"));

        // Pedido 1: pendiente; sirve para probar el flujo de Cocina.
        Pedido pendiente = pedidos.crear(1, 1, TipoPedido.SALON);
        pedidos.agregarProducto(pendiente.getId(), 3, 2); pedidos.agregarProducto(pendiente.getId(), 6, 2);

        // Pedido 2: listo; sirve para probar entrega en salón.
        Pedido listoSalon = pedidos.crear(2, 2, TipoPedido.SALON);
        pedidos.agregarProducto(listoSalon.getId(), 4, 1); pedidos.agregarProducto(listoSalon.getId(), 7, 1);
        pedidos.cambiarEstado(listoSalon.getId(), EstadoPedido.EN_PREPARACION); pedidos.cambiarEstado(listoSalon.getId(), EstadoPedido.LISTO);

        // Pedido 3: en camino; sirve para probar entrega a domicilio.
        Pedido domicilio = pedidos.crear(0, 1, TipoPedido.DOMICILIO);
        pedidos.agregarProducto(domicilio.getId(), 5, 1); pedidos.agregarProducto(domicilio.getId(), 6, 1);
        pedidos.cambiarEstado(domicilio.getId(), EstadoPedido.EN_PREPARACION); pedidos.cambiarEstado(domicilio.getId(), EstadoPedido.LISTO);
        pedidos.asignarDomiciliario(domicilio.getId(), 4);

        // Pedido 4: entregado; aparece en el reporte de ventas y tiempos.
        Pedido entregado = pedidos.crear(0, 2, TipoPedido.PARA_LLEVAR);
        pedidos.agregarProducto(entregado.getId(), 1, 3); pedidos.agregarProducto(entregado.getId(), 8, 2);
        pedidos.cambiarEstado(entregado.getId(), EstadoPedido.EN_PREPARACION); pedidos.cambiarEstado(entregado.getId(), EstadoPedido.LISTO);
        pedidos.cambiarEstado(entregado.getId(), EstadoPedido.ENTREGADO);

        // Pedido 5: cancelado desde PENDIENTE; permite validar la regla de cancelación.
        Pedido cancelado = pedidos.crear(3, 1, TipoPedido.SALON);
        pedidos.agregarProducto(cancelado.getId(), 2, 1); pedidos.cancelar(cancelado.getId());
    }

    public static String guia() {
        return "\nDATOS DE PRUEBA (IDs fijos)\n"
                + "Usuarios: 1 Mesero Ana (mesero1/1234), 2 Mesero David (mesero2/1234), "
                + "3 Cocina Luis (cocina/1234), 4 Domiciliario Sofía (domiciliario/1234), 5 Admin Carlos (admin/1234).\n"
                + "Categorías: 1 Entradas, 2 Platos fuertes, 3 Bebidas.\n"
                + "Productos: 1 Empanadas, 2 Nachos, 3 Hamburguesa, 4 Pizza, 5 Pasta, 6 Limonada, 7 Gaseosa, 8 Agua, 9 Café.\n"
                + "Mesas: IDs 1 a 5. Pedidos: 1 Pendiente, 2 Listo salón, 3 En camino domicilio, 4 Entregado para llevar, 5 Cancelado.\n";
    }

    private static Categoria categoria(int id, String nombre, String descripcion) { Categoria c = new Categoria(nombre, descripcion); c.setId(id); return c; }
    private static Producto producto(int id, String nombre, double precio, int categoria) { Producto p = new Producto(nombre, precio, categoria); p.setId(id); return p; }
    private static Mesa mesa(int id, int numero, int capacidad) { Mesa m = new Mesa(numero, capacidad); m.setId(id); return m; }
    private static Mesero usuarioMesero(int id, String nombre, String login) { Mesero u = new Mesero(nombre, login, "1234"); u.setId(id); return u; }
    private static Cocina usuarioCocina(int id, String nombre, String login) { Cocina u = new Cocina(nombre, login, "1234"); u.setId(id); return u; }
    private static Domiciliario usuarioDomiciliario(int id, String nombre, String login, String telefono) { Domiciliario u = new Domiciliario(nombre, login, "1234", telefono); u.setId(id); return u; }
    private static Administrador usuarioAdmin(int id, String nombre, String login) { Administrador u = new Administrador(nombre, login, "1234"); u.setId(id); return u; }
}
