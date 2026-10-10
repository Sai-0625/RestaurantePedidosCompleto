package co.edu.poligran.paradigmas.restaurante;

import co.edu.poligran.paradigmas.restaurante.modelo.*;
import co.edu.poligran.paradigmas.restaurante.negocio.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/** Único punto de entrada: integra las pantallas de consola de todos los managers. */
public class RestauranteApp {
    private static final Scanner SC = new Scanner(System.in);
    private static final DatosRestaurante DATOS = new DatosRestaurante();
    private static final UsuariosManager USUARIOS = new UsuariosManager(DATOS);
    private static final CatalogoManager CATALOGO = new CatalogoManager(DATOS);
    private static final MesasManager MESAS = new MesasManager(DATOS);
    private static final PedidosManager PEDIDOS = new PedidosManager(DATOS);
    private static final ReportesManager REPORTES = new ReportesManager(DATOS);

    public static void main(String[] args) {
        DatosPrueba.cargar(USUARIOS, CATALOGO, MESAS);
        System.out.println("Datos de prueba cargados. Mesero: mesero / 1234 | Domiciliario: domiciliario / 1234");
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE RESTAURANTE ===\n1. Usuarios\n2. Catálogo\n3. Mesas\n4. Pedidos\n5. Reportes\n0. Salir");
            opcion = entero("Opción: ");
            try {
                switch (opcion) { case 1: menuUsuarios(); break; case 2: menuCatalogo(); break; case 3: menuMesas(); break; case 4: menuPedidos(); break; case 5: menuReportes(); break; case 0: break; default: System.out.println("Opción inválida."); }
            } catch (IllegalArgumentException | IllegalStateException e) { System.out.println("Error: " + e.getMessage()); }
        } while (opcion != 0);
    }

    private static void menuUsuarios() {
        System.out.println("\n1. Registrar  2. Listar  3. Iniciar sesión  4. Eliminar");
        switch (entero("Opción: ")) {
            case 1:
                String nombre=texto("Nombre: "), usuario=texto("Usuario: "), clave=texto("Contraseña: ");
                Rol rol=Rol.values()[entero("Rol (0 Mesero, 1 Cocina, 2 Administrador, 3 Domiciliario): ")];
                Usuario nuevo;
                if(rol==Rol.DOMICILIARIO) nuevo=new Domiciliario(nombre,usuario,clave,texto("Teléfono: "));
                else if(rol==Rol.MESERO) nuevo=new Mesero(nombre,usuario,clave);
                else if(rol==Rol.COCINA) nuevo=new Cocina(nombre,usuario,clave);
                else nuevo=new Administrador(nombre,usuario,clave);
                System.out.println("Registrado con ID " + USUARIOS.registrar(nuevo).getId()); break;
            case 2: mostrar(USUARIOS.listar()); break;
            case 3: System.out.println(USUARIOS.iniciarSesion(texto("Usuario: "),texto("Contraseña: ")) != null ? "Sesión iniciada." : "Credenciales incorrectas."); break;
            case 4: System.out.println(USUARIOS.eliminar(entero("ID: ")) ? "Usuario eliminado." : "No existe."); break;
            default: break;
        }
    }

    private static void menuCatalogo() {
        System.out.println("\n1. Crear categoría  2. Listar categorías  3. Crear producto  4. Listar productos  5. Eliminar producto");
        switch(entero("Opción: ")) {
            case 1: System.out.println("Categoría ID " + CATALOGO.crearCategoria(new Categoria(texto("Nombre: "),texto("Descripción: "))).getId()); break;
            case 2: mostrar(CATALOGO.listarCategorias()); break;
            case 3: System.out.println("Producto ID " + CATALOGO.crearProducto(new Producto(texto("Nombre: "),decimal("Precio: "),entero("ID categoría: "))).getId()); break;
            case 4: mostrar(CATALOGO.listarProductos()); break;
            case 5: System.out.println(CATALOGO.eliminarProducto(entero("ID producto: ")) ? "Producto eliminado." : "No existe."); break;
            default: break;
        }
    }

    private static void menuMesas() {
        System.out.println("\n1. Crear mesa  2. Listar mesas  3. Eliminar mesa");
        switch(entero("Opción: ")) {
            case 1: System.out.println("Mesa ID " + MESAS.crear(new Mesa(entero("Número: "),entero("Capacidad: "))).getId()); break;
            case 2: mostrar(MESAS.listar()); break;
            case 3: System.out.println(MESAS.eliminar(entero("ID mesa: ")) ? "Mesa eliminada." : "No existe."); break;
            default: break;
        }
    }

    private static void menuPedidos() {
        System.out.println("\n1. Crear pedido  2. Agregar producto  3. Ver pendientes  4. Pasar a preparación  5. Marcar listo\n6. Asignar domiciliario  7. Entregar salón  8. Entregar domicilio  9. Cancelar  10. Ver total  11. Listar pedidos");
        int opcion=entero("Opción: ");
        switch(opcion) {
            case 1: TipoPedido tipo=TipoPedido.values()[entero("Tipo (0 Salón, 1 Para llevar, 2 Domicilio): ")]; int mesa=tipo==TipoPedido.SALON?entero("ID mesa: "):0; System.out.println("Pedido ID " + PEDIDOS.crear(mesa,entero("ID mesero: "),tipo).getId()); break;
            case 2: System.out.println("Detalle ID " + PEDIDOS.agregarProducto(entero("ID pedido: "),entero("ID producto: "),entero("Cantidad: ")).getId()); break;
            case 3: mostrar(PEDIDOS.verPendientes()); break;
            case 4: PEDIDOS.cambiarEstado(entero("ID pedido: "),EstadoPedido.EN_PREPARACION); System.out.println("Pedido en preparación."); break;
            case 5: PEDIDOS.cambiarEstado(entero("ID pedido: "),EstadoPedido.LISTO); System.out.println("Pedido listo."); break;
            case 6: PEDIDOS.asignarDomiciliario(entero("ID pedido: "),entero("ID domiciliario: ")); System.out.println("Domiciliario asignado."); break;
            case 7: PEDIDOS.entregarSalon(entero("ID pedido: ")); System.out.println("Pedido entregado."); break;
            case 8: PEDIDOS.entregarDomicilio(entero("ID pedido: ")); System.out.println("Pedido entregado."); break;
            case 9: PEDIDOS.cancelar(entero("ID pedido: ")); System.out.println("Pedido cancelado."); break;
            case 10: System.out.println("Total: $" + PEDIDOS.calcularTotal(entero("ID pedido: "))); break;
            case 11: mostrar(PEDIDOS.listar()); break;
            default: break;
        }
    }

    private static void menuReportes() {
        LocalDate inicio=fecha("Fecha inicio (AAAA-MM-DD): "), fin=fecha("Fecha fin (AAAA-MM-DD): ");
        System.out.println("Ventas: $" + REPORTES.generarVentas(inicio,fin).getVentasTotales());
        ReporteTiempos tiempos=REPORTES.generarTiempos(inicio,fin);
        System.out.println("Pedidos entregados: " + tiempos.getNumeroPedidos());
        System.out.println("Promedio preparación: " + tiempos.getPromedioPreparacionMinutos() + " min");
        System.out.println("Promedio entrega: " + tiempos.getPromedioEntregaMinutos() + " min");
    }
    private static void mostrar(List<?> lista) { if(lista.isEmpty()) System.out.println("Sin registros."); else for(Object o:lista) System.out.println(o); }
    private static String texto(String mensaje) { System.out.print(mensaje); return SC.nextLine().trim(); }
    private static int entero(String mensaje) { return Integer.parseInt(texto(mensaje)); }
    private static double decimal(String mensaje) { return Double.parseDouble(texto(mensaje)); }
    private static LocalDate fecha(String mensaje) { return LocalDate.parse(texto(mensaje)); }
}
