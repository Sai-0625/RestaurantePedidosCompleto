package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Categoria;
public class CategoriaDAOImpl extends GenericDAOImpl<Categoria> implements CategoriaDAO {
 public Categoria buscarPorNombre(String nombre){for(Categoria c:datos)if(c.getNombre().equalsIgnoreCase(nombre))return c;return null;}
}
