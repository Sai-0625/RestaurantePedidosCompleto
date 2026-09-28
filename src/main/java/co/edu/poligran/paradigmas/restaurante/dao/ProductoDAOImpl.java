package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Producto; import java.util.ArrayList; import java.util.List;
public class ProductoDAOImpl extends GenericDAOImpl<Producto> implements ProductoDAO { public List<Producto> listarPorCategoria(int id){List<Producto> r=new ArrayList<>();for(Producto p:datos)if(p.getIdCategoria()==id)r.add(p);return r;} }
