package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.EstadoPedido; import co.edu.poligran.paradigmas.restaurante.modelo.Pedido; import java.util.ArrayList; import java.util.List;
public class PedidoDAOImpl extends GenericDAOImpl<Pedido> implements PedidoDAO { public List<Pedido> listarPorEstado(EstadoPedido e){List<Pedido> r=new ArrayList<>();for(Pedido p:datos)if(p.getEstado()==e)r.add(p);return r;} }
