package co.edu.poligran.paradigmas.restaurante.modelo;
import java.time.LocalDateTime;
public class Pedido implements Identificable {
    private int id; private int idMesa; private int idMesero; private Integer idDomiciliario; private TipoPedido tipo = TipoPedido.SALON; private EstadoPedido estado=EstadoPedido.PENDIENTE; private LocalDateTime fecha=LocalDateTime.now();
    public Pedido() { } public Pedido(int idMesa,int idMesero){this.idMesa=idMesa;this.idMesero=idMesero;} public Pedido(int idMesa,int idMesero,TipoPedido tipo){this(idMesa,idMesero);this.tipo=tipo;}
    public double calcularTotal(java.util.List<DetallePedido> detalles) { double total=0; for(DetallePedido detalle:detalles) total+=detalle.getSubtotal(); return total; }
    public int getId(){return id;} public void setId(int v){id=v;} public int getIdMesa(){return idMesa;} public void setIdMesa(int v){idMesa=v;} public int getIdMesero(){return idMesero;} public void setIdMesero(int v){idMesero=v;} public Integer getIdDomiciliario(){return idDomiciliario;} public void setIdDomiciliario(Integer v){idDomiciliario=v;} public TipoPedido getTipo(){return tipo;} public void setTipo(TipoPedido v){tipo=v;} public EstadoPedido getEstado(){return estado;} public void setEstado(EstadoPedido v){estado=v;} public LocalDateTime getFecha(){return fecha;}
    @Override public String toString(){return "Pedido "+id+" | "+tipo+" | "+estado+" | mesa "+idMesa;}
}
