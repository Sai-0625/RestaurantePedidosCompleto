package co.edu.poligran.paradigmas.restaurante.modelo;
public class DetallePedido implements Identificable {
    private int id; private int idPedido; private int idProducto; private int cantidad; private double precioUnitario;
    public DetallePedido() { } public DetallePedido(int idProducto,int cantidad,double precioUnitario){this.idProducto=idProducto;this.cantidad=cantidad;this.precioUnitario=precioUnitario;}
    public double getSubtotal(){return cantidad*precioUnitario;} public int getId(){return id;} public void setId(int v){id=v;} public int getIdPedido(){return idPedido;} public void setIdPedido(int v){idPedido=v;} public int getIdProducto(){return idProducto;} public void setIdProducto(int v){idProducto=v;} public int getCantidad(){return cantidad;} public void setCantidad(int v){cantidad=v;} public double getPrecioUnitario(){return precioUnitario;} public void setPrecioUnitario(double v){precioUnitario=v;}
}
