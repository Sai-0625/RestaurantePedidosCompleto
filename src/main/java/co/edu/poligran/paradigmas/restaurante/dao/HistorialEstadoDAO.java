package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.HistorialEstado; import java.util.List;
public interface HistorialEstadoDAO extends GenericDAO<HistorialEstado> { List<HistorialEstado> listarPorPedido(int idPedido); }
