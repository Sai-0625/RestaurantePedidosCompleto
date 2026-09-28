package co.edu.poligran.paradigmas.restaurante.modelo;
public class Mesa implements Identificable {
    private int id; private int numero; private int capacidad; private boolean ocupada;
    public Mesa() { } public Mesa(int numero,int capacidad){this.numero=numero;this.capacidad=capacidad;}
    public int getId(){return id;} public void setId(int v){id=v;} public int getNumero(){return numero;} public void setNumero(int v){numero=v;} public int getCapacidad(){return capacidad;} public void setCapacidad(int v){capacidad=v;} public boolean isOcupada(){return ocupada;} public void setOcupada(boolean v){ocupada=v;}
}
