package co.edu.poligran.paradigmas.restaurante.modelo;
public class Mesa implements Identificable {
    private int id; private int numero; private int capacidad; private EstadoMesa estado = EstadoMesa.LIBRE;
    public Mesa() { } public Mesa(int numero,int capacidad){this.numero=numero;this.capacidad=capacidad;}
    public int getId(){return id;} public void setId(int v){id=v;} public int getNumero(){return numero;} public void setNumero(int v){numero=v;} public int getCapacidad(){return capacidad;} public void setCapacidad(int v){capacidad=v;}
    public EstadoMesa getEstado(){return estado;} public void setEstado(EstadoMesa v){estado=v;}
    public boolean isOcupada(){return estado == EstadoMesa.OCUPADA;} public void setOcupada(boolean v){estado=v ? EstadoMesa.OCUPADA : EstadoMesa.LIBRE;}
    @Override public String toString(){return "["+id+"] Mesa "+numero+" | capacidad "+capacidad+" | "+estado;}
}
