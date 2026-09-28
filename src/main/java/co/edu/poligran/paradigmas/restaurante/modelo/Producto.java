package co.edu.poligran.paradigmas.restaurante.modelo;
public class Producto implements Identificable {
    private int id; private String nombre; private double precio; private int idCategoria; private boolean disponible=true;
    public Producto() { } public Producto(String nombre,double precio,int idCategoria){this.nombre=nombre;this.precio=precio;this.idCategoria=idCategoria;}
    public int getId(){return id;} public void setId(int v){id=v;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;} public double getPrecio(){return precio;} public void setPrecio(double v){precio=v;} public int getIdCategoria(){return idCategoria;} public void setIdCategoria(int v){idCategoria=v;} public boolean isDisponible(){return disponible;} public void setDisponible(boolean v){disponible=v;}
}
