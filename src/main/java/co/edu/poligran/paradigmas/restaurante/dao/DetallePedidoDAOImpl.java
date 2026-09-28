package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.DetallePedido; import java.util.ArrayList; import java.util.List;
public class DetallePedidoDAOImpl extends GenericDAOImpl<DetallePedido> implements DetallePedidoDAO { public List<DetallePedido> listarPorPedido(int id){List<DetallePedido> r=new ArrayList<>();for(DetallePedido d:datos)if(d.getIdPedido()==id)r.add(d);return r;} }
