package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.DetallePedido; import java.util.List;
public interface DetallePedidoDAO extends GenericDAO<DetallePedido> { List<DetallePedido> listarPorPedido(int idPedido); }
