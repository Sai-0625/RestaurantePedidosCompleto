package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.HistorialEstado; import java.util.ArrayList; import java.util.List;
public class HistorialEstadoDAOImpl extends GenericDAOImpl<HistorialEstado> implements HistorialEstadoDAO { public List<HistorialEstado> listarPorPedido(int id){List<HistorialEstado> r=new ArrayList<>();for(HistorialEstado h:datos)if(h.getIdPedido()==id)r.add(h);return r;} }
