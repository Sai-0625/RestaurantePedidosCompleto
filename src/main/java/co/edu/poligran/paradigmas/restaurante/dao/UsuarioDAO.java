package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Usuario;
public interface UsuarioDAO extends GenericDAO<Usuario> { Usuario buscarPorUsuario(String usuario); }
