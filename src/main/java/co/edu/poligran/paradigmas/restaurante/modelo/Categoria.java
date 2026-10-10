package co.edu.poligran.paradigmas.restaurante.modelo;
public class Categoria implements Identificable {
    private int id; private String nombre; private String descripcion;
    public Categoria() { } public Categoria(String nombre, String descripcion) { this.nombre=nombre; this.descripcion=descripcion; }
    public int getId(){return id;} public void setId(int v){id=v;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;} public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
    @Override public String toString(){return "["+id+"] "+nombre+" - "+descripcion;}
}
