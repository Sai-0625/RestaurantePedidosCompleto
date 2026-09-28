package co.edu.poligran.paradigmas.restaurante.modelo;
import java.time.LocalDateTime;
public class HistorialEstado implements Identificable {
    private int id; private int idPedido; private EstadoPedido estado; private LocalDateTime fecha=LocalDateTime.now();
    public HistorialEstado() { } public HistorialEstado(int idPedido,EstadoPedido estado){this.idPedido=idPedido;this.estado=estado;}
    public int getId(){return id;} public void setId(int v){id=v;} public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;} public EstadoPedido getEstado(){return estado;} public void setEstado(EstadoPedido v){estado=v;} public LocalDateTime getFecha(){return fecha;}
}
