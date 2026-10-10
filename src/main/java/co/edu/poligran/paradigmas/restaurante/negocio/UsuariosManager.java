package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
import java.util.List;

/** Gestiona usuarios, inicio de sesión y cierre de sesión. */
public class UsuariosManager {
    private final DatosRestaurante datos;
    public UsuariosManager(DatosRestaurante datos) { this.datos = datos; }
    public Usuario registrar(Usuario usuario) {
        if (usuario == null || usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) throw new IllegalArgumentException("El usuario es obligatorio");
        if (datos.usuarios.buscarPorUsuario(usuario.getUsuario()) != null) throw new IllegalArgumentException("El nombre de usuario ya existe");
        return datos.usuarios.crear(usuario);
    }
    public Usuario iniciarSesion(String usuario, String contrasena) {
        Usuario encontrado = datos.usuarios.buscarPorUsuario(usuario);
        return encontrado != null && encontrado.iniciarSesion(usuario, contrasena) ? encontrado : null;
    }
    public void cerrarSesion(Usuario usuario) { if (usuario != null) usuario.cerrarSesion(); }
    public List<Usuario> listar() { return datos.usuarios.listar(); }
    public Usuario buscar(int id) { return datos.usuarios.buscarPorId(id); }
    public boolean actualizar(Usuario usuario) { return datos.usuarios.actualizar(usuario) != null; }
    public boolean eliminar(int id) { return datos.usuarios.eliminar(id); }
}
