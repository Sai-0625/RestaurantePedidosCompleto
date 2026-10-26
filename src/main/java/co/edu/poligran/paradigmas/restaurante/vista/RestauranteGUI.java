package co.edu.poligran.paradigmas.restaurante.vista;

import co.edu.poligran.paradigmas.restaurante.negocio.*;
import javax.swing.SwingUtilities;

/** Punto de entrada de la interfaz gráfica: arma los managers y abre la ventana principal. */
public class RestauranteGUI {
    public static void main(String[] args) {
        // Toda la interfaz se crea en el Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            DatosRestaurante datos = new DatosRestaurante();
            UsuariosManager usuarios = new UsuariosManager(datos);
            CatalogoManager catalogo = new CatalogoManager(datos);
            MesasManager mesas = new MesasManager(datos);
            PedidosManager pedidos = new PedidosManager(datos);
            ReportesManager reportes = new ReportesManager(datos);
            DatosPrueba.cargar(usuarios, catalogo, mesas, pedidos);

            VentanaPrincipal ventana = new VentanaPrincipal(usuarios, catalogo, mesas, pedidos, reportes);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
