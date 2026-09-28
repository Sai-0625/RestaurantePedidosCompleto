package co.edu.poligran.paradigmas.restaurante.modelo;
import java.time.LocalDateTime;
public class Pedido implements Identificable {
    private int id; private int idMesa; private int idMesero; private Integer idDomiciliario; private EstadoPedido estado=EstadoPedido.PENDIENTE; private LocalDateTime fecha=LocalDateTime.now();
    public Pedido() { } public Pedido(int idMesa,int idMesero){this.idMesa=idMesa;this.idMesero=idMesero;}
    public int getId(){return id;} public void setId(int v){id=v;} public int getIdMesa(){return idMesa;} public void setIdMesa(int v){idMesa=v;} public int getIdMesero(){return idMesero;} public void setIdMesero(int v){idMesero=v;} public Integer getIdDomiciliario(){return idDomiciliario;} public void setIdDomiciliario(Integer v){idDomiciliario=v;} public EstadoPedido getEstado(){return estado;} public void setEstado(EstadoPedido v){estado=v;} public LocalDateTime getFecha(){return fecha;}
}
