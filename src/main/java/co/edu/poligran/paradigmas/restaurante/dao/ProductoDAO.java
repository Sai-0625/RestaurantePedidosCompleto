package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Producto; import java.util.List;
public interface ProductoDAO extends GenericDAO<Producto> { List<Producto> listarPorCategoria(int idCategoria); }
