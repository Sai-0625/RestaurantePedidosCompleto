package co.edu.poligran.paradigmas.restaurante.consola;

import java.util.List;
import java.util.Scanner;

import co.edu.poligran.paradigmas.restaurante.modelo.DetallePedido;
import co.edu.poligran.paradigmas.restaurante.modelo.HistorialEstado;
import co.edu.poligran.paradigmas.restaurante.modelo.Pedido;
import co.edu.poligran.paradigmas.restaurante.modelo.TipoPedido;
import co.edu.poligran.paradigmas.restaurante.negocio.MeseroManager;

/**
 * Pantalla de consola del MESERO.
 * Solo muestra el menu pide los datos con Scanner y llama a MeseroManager
 * Recibe el Scanner de RestauranteApp
 */
public class PantallaMesero {

    private final Scanner sc;
    private final MeseroManager manager;

    public PantallaMesero(Scanner sc, MeseroManager manager) {
        this.sc = sc;
        this.manager = manager;
    }

    
    public void mostrar() {
        int opcion;
        do {
            System.out.println("\n=== PANTALLA MESERO ===");
            System.out.println("1. Crear pedido");
            System.out.println("2. Agregar producto a un pedido");
            System.out.println("3. Quitar producto de un pedido");
            System.out.println("4. Asignar / cambiar mesa");
            System.out.println("5. Marcar pedido como entregado");
            System.out.println("   CONSULTAS Y CAMBIOS DE PEDIDOS:  ");
            System.out.println("6. Ver detalle y total de un pedido");
            System.out.println("7. Cambiar cantidad de un producto");
            System.out.println("8. Ver historial de estados de un pedido");
            System.out.println("9. Ver mis pedidos");
            System.out.println("10. Eliminar pedido (solo pendientes)");
            System.out.println("0. Volver");
            opcion = leerOpcion();
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                int idMesero = entero("ID del mesero: ");
                TipoPedido tipo = pedirTipo();
                int idMesa = tipo == TipoPedido.SALON ? entero("ID de la mesa: ") : 0;
                Pedido nuevo = manager.crearPedido(idMesero, tipo, idMesa);
                System.out.println("Pedido creado con ID " + nuevo.getId());
                break;
            case 2:
                int idPedido = entero("ID del pedido: ");
                int idProducto = entero("ID del producto: ");
                int cantidad = entero("Cantidad: ");
                DetallePedido detalle = manager.agregarProducto(idPedido, idProducto, cantidad);
                System.out.println("Linea " + detalle.getId() + ": ahora lleva " + detalle.getCantidad() + " unidad(es).");
                break;
            case 3:
                boolean quitado = manager.quitarProducto(entero("ID del pedido: "), entero("ID del producto: "));
                System.out.println(quitado ? "Producto quitado." : "No se pudo quitar.");
                break;
            case 4:
                Pedido conMesa = manager.asignarMesa(entero("ID del pedido: "), entero("ID de la nueva mesa: "));
                System.out.println("Pedido " + conMesa.getId() + " ahora esta en la mesa " + conMesa.getIdMesa());
                break;
            case 5:
                Pedido entregado = manager.marcarComoEntregado(entero("ID del pedido: "));
                System.out.println("Pedido " + entregado.getId() + " entregado.");
                break;
            case 6:
                int id = entero("ID del pedido: ");
                List<DetallePedido> lineas = manager.verDetalle(id);
                if (lineas.isEmpty()) {
                    System.out.println("El pedido todavia no tiene productos.");
                }
                for (DetallePedido linea : lineas) {
                    System.out.println("Producto " + linea.getIdProducto() + " | cantidad " + linea.getCantidad()
                            + " | $" + linea.getPrecioUnitario() + " c/u | subtotal $" + linea.getSubtotal());
                }
                System.out.println("Total: $" + manager.calcularTotal(id));
                break;
            case 7:
                DetallePedido cambiado = manager.cambiarCantidad(
                        entero("ID del pedido: "), entero("ID del producto: "), entero("Nueva cantidad: "));
                System.out.println("Cantidad actualizada: " + cambiado.getCantidad());
                break;
            case 8:
                for (HistorialEstado cambio : manager.verHistorial(entero("ID del pedido: "))) {
                    System.out.println(cambio.getFecha() + " -> " + cambio.getEstado());
                }
                break;
            case 9:
                mostrar(manager.listarMisPedidos(entero("ID del mesero: ")));
                break;
            case 10:
                boolean eliminado = manager.eliminarPedido(entero("ID del pedido: "));
                System.out.println(eliminado ? "Pedido eliminado." : "No existe.");
                break;
            case 0:
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private TipoPedido pedirTipo() {
        int t = entero("Tipo (0 Salon, 1 Para llevar, 2 Domicilio): ");
        if (t < 0 || t >= TipoPedido.values().length) {
            throw new IllegalArgumentException("Tipo de pedido invalido");
        }
        return TipoPedido.values()[t];
    }

    private void mostrar(List<?> lista) {
        if (lista.isEmpty()) {
            System.out.println("Sin registros.");
        }
        for (Object elemento : lista) {
            System.out.println(elemento);
        }
    }

    /** Lee la opcion del menu si escriben letras devuelve -1 opcion invalida en vez de romper */
    private int leerOpcion() {
        System.out.print("Opcion: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Lee un entero, si escriben letras lanza IllegalArgumentException */
    private int entero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Debe escribir un numero entero");
        }
    }
}