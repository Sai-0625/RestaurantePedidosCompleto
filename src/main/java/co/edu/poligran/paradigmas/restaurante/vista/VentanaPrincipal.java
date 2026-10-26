package co.edu.poligran.paradigmas.restaurante.vista;

import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
import co.edu.poligran.paradigmas.restaurante.negocio.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Ventana principal: JFrame con barra de menú, pestañas por módulo y barra de estado con el usuario en sesión.
 * Cada pestaña es por ahora un panel provisional que se reemplaza en los pasos siguientes.
 */
public class VentanaPrincipal extends JFrame {
    private static final String[] MODULOS = { "Usuarios", "Catálogo", "Mesas", "Pedidos", "Reportes" };
    private static final String[] PASOS = { "03", "04", "05", "06", "08" };

    private final Usuario sesion;
    private final Runnable alCerrarSesion;

    // Los managers se guardan para que los paneles de los pasos siguientes los usen.
    private final UsuariosManager usuarios;
    private final CatalogoManager catalogo;
    private final MesasManager mesas;
    private final PedidosManager pedidos;
    private final ReportesManager reportes;

    private final JTabbedPane pestanas = new JTabbedPane();
    private final JLabel estado = new JLabel(" ");

    public VentanaPrincipal(Usuario sesion, UsuariosManager usuarios, CatalogoManager catalogo, MesasManager mesas, PedidosManager pedidos, ReportesManager reportes, Runnable alCerrarSesion) {
        super("Sistema de Restaurante – " + sesion.getNombre());
        this.sesion = sesion; this.alCerrarSesion = alCerrarSesion;
        this.usuarios = usuarios; this.catalogo = catalogo; this.mesas = mesas; this.pedidos = pedidos; this.reportes = reportes;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(crearMenu());

        for (int i = 0; i < MODULOS.length; i++) pestanas.addTab(MODULOS[i], panelPendiente(MODULOS[i], PASOS[i]));
        pestanas.addChangeListener(e -> actualizarEstado());
        pestanas.setPreferredSize(new Dimension(800, 500));

        estado.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(pestanas, BorderLayout.CENTER);
        add(estado, BorderLayout.SOUTH);
        actualizarEstado();
    }

    private JMenuBar crearMenu() {
        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic(KeyEvent.VK_A);
        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.addActionListener(e -> cerrarSesion());
        JMenuItem salir = new JMenuItem("Salir");
        salir.addActionListener(e -> dispose());
        archivo.add(cerrarSesion);
        archivo.addSeparator();
        archivo.add(salir);

        JMenu ver = new JMenu("Ver");
        ver.setMnemonic(KeyEvent.VK_V);
        for (int i = 0; i < MODULOS.length; i++) {
            int indice = i;
            JMenuItem item = new JMenuItem(MODULOS[i]);
            item.addActionListener(e -> pestanas.setSelectedIndex(indice));
            ver.add(item);
        }

        JMenu ayuda = new JMenu("Ayuda");
        ayuda.setMnemonic(KeyEvent.VK_Y);
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        acercaDe.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema de Restaurante\nPoligran – Paradigmas de programación", "Acerca de", JOptionPane.INFORMATION_MESSAGE));
        ayuda.add(acercaDe);

        JMenuBar barra = new JMenuBar();
        barra.add(archivo); barra.add(ver); barra.add(ayuda);
        return barra;
    }

    private JPanel panelPendiente(String modulo, String paso) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel mensaje = new JLabel("Módulo de " + modulo + ": se construye en el paso " + paso + ".", SwingConstants.CENTER);
        panel.add(mensaje, BorderLayout.CENTER);
        return panel;
    }

    /** Cierra esta ventana y vuelve a pedir el inicio de sesión. */
    private void cerrarSesion() {
        usuarios.cerrarSesion(sesion);
        dispose();
        alCerrarSesion.run();
    }

    private void actualizarEstado() {
        estado.setText("Usuario: " + sesion.getNombre() + " (" + sesion.getRol() + ")   |   Módulo: " + MODULOS[pestanas.getSelectedIndex()] + "   |   Pedidos registrados: " + pedidos.listar().size());
    }
}
