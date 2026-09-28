package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.EstadoPedido; import co.edu.poligran.paradigmas.restaurante.modelo.Pedido; import java.util.List;
public interface PedidoDAO extends GenericDAO<Pedido> { List<Pedido> listarPorEstado(EstadoPedido estado); }
