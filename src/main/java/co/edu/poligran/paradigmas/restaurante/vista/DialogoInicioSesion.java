package co.edu.poligran.paradigmas.restaurante.vista;

import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
import co.edu.poligran.paradigmas.restaurante.negocio.UsuariosManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.*;

/** Diálogo modal que pide usuario y contraseña; bloquea hasta que se inicia sesión o se cancela. */
public class DialogoInicioSesion extends JDialog {
    private final UsuariosManager usuarios;
    private final JTextField campoUsuario = new JTextField(15);
    private final JPasswordField campoContrasena = new JPasswordField(15);
    private Usuario usuarioIniciado;

    public DialogoInicioSesion(UsuariosManager usuarios) {
        super((JFrame) null, "Iniciar sesión", true);
        this.usuarios = usuarios;
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Formulario: dos filas de etiqueta + campo.
        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        formulario.add(new JLabel("Usuario:"));
        formulario.add(campoUsuario);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(campoContrasena);

        JButton ingresar = new JButton("Ingresar");
        JButton cancelar = new JButton("Cancelar");
        ingresar.addActionListener(e -> ingresar());
        cancelar.addActionListener(e -> dispose());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(cancelar);
        botones.add(ingresar);

        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(ingresar); // Enter equivale a pulsar "Ingresar".
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /** Muestra el diálogo y devuelve el usuario que inició sesión, o null si se canceló. */
    public Usuario mostrar() {
        setVisible(true); // Al ser modal, espera aquí hasta que el diálogo se cierra.
        return usuarioIniciado;
    }

    private void ingresar() {
        char[] contrasena = campoContrasena.getPassword();
        Usuario usuario = usuarios.iniciarSesion(campoUsuario.getText().trim(), new String(contrasena));
        Arrays.fill(contrasena, ' '); // No dejar la contraseña en memoria más tiempo del necesario.
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Inicio de sesión", JOptionPane.ERROR_MESSAGE);
            campoContrasena.setText("");
            campoContrasena.requestFocusInWindow();
            return;
        }
        usuarioIniciado = usuario;
        dispose();
    }
}
