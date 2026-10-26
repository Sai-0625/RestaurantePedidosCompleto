package co.edu.poligran.paradigmas.restaurante.vista;

import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
import co.edu.poligran.paradigmas.restaurante.negocio.*;
import javax.swing.SwingUtilities;

/** Punto de entrada de la interfaz gráfica: arma los managers, pide el inicio de sesión y abre la ventana principal. */
public class RestauranteGUI {
    private final UsuariosManager usuarios;
    private final CatalogoManager catalogo;
    private final MesasManager mesas;
    private final PedidosManager pedidos;
    private final ReportesManager reportes;

    private RestauranteGUI() {
        DatosRestaurante datos = new DatosRestaurante();
        usuarios = new UsuariosManager(datos);
        catalogo = new CatalogoManager(datos);
        mesas = new MesasManager(datos);
        pedidos = new PedidosManager(datos);
        reportes = new ReportesManager(datos);
        DatosPrueba.cargar(usuarios, catalogo, mesas, pedidos);
    }

    public static void main(String[] args) {
        // Toda la interfaz se crea en el Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> new RestauranteGUI().iniciarSesion());
    }

    /** Pide usuario y contraseña; si se cancela, la aplicación termina porque no queda ninguna ventana abierta. */
    private void iniciarSesion() {
        Usuario usuario = new DialogoInicioSesion(usuarios).mostrar();
        if (usuario == null) return;

        VentanaPrincipal ventana = new VentanaPrincipal(usuario, usuarios, catalogo, mesas, pedidos, reportes, this::iniciarSesion);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
