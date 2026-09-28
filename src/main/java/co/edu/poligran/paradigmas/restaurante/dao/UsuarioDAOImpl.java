package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
public class UsuarioDAOImpl extends GenericDAOImpl<Usuario> implements UsuarioDAO { public Usuario buscarPorUsuario(String usuario){for(Usuario u:datos)if(u.getUsuario().equalsIgnoreCase(usuario))return u;return null;} }
