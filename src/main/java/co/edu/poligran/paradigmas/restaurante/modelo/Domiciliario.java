package co.edu.poligran.paradigmas.restaurante.modelo;

/** Especialización de Usuario para entregas a domicilio. */
public class Domiciliario extends Usuario {
    private String telefono; private boolean disponible=true;
    public Domiciliario(String nombre, String usuario, String contrasena, String telefono) { super(nombre, usuario, contrasena, Rol.DOMICILIARIO); this.telefono=telefono; }
    public String getTelefono() { return telefono; } public void setTelefono(String valor) { telefono=valor; }
    public boolean isDisponible() { return disponible; } public void setDisponible(boolean valor) { disponible=valor; }
}
