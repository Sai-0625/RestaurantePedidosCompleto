package co.edu.poligran.paradigmas.restaurante.modelo;

/** Clase padre para mesero, cocina, administrador y domiciliario. */
public class Usuario implements Identificable {
    private int id; private String nombre; private String usuario; private String contrasena; private Rol rol;
    public Usuario() { }
    public Usuario(String nombre, String usuario, String contrasena, Rol rol) { this.nombre=nombre; this.usuario=usuario; this.contrasena=contrasena; this.rol=rol; }
    public boolean iniciarSesion(String usuario, String contrasena) { return this.usuario.equals(usuario) && this.contrasena.equals(contrasena); }
    public void cerrarSesion() { }
    public int getId() { return id; } public void setId(int id) { this.id=id; }
    public String getNombre() { return nombre; } public void setNombre(String valor) { nombre=valor; }
    public String getUsuario() { return usuario; } public void setUsuario(String valor) { usuario=valor; }
    public String getContrasena() { return contrasena; } public void setContrasena(String valor) { contrasena=valor; }
    public Rol getRol() { return rol; } public void setRol(Rol valor) { rol=valor; }
}
