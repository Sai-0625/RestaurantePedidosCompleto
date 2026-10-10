package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.Administrador;
import co.edu.poligran.paradigmas.restaurante.modelo.Categoria;
import co.edu.poligran.paradigmas.restaurante.modelo.Cocina;
import co.edu.poligran.paradigmas.restaurante.modelo.Domiciliario;
import co.edu.poligran.paradigmas.restaurante.modelo.Mesa;
import co.edu.poligran.paradigmas.restaurante.modelo.Mesero;
import co.edu.poligran.paradigmas.restaurante.modelo.Producto;

/** Carga registros locales para demostrar y probar las funcionalidades. */
public final class DatosPrueba {
    private DatosPrueba() { }

    public static void cargar(UsuariosManager usuarios, CatalogoManager catalogo, MesasManager mesas) {
        if (!catalogo.listarProductos().isEmpty()) return;

        Categoria entradas = catalogo.crearCategoria(new Categoria("Entradas", "Aperitivos para comenzar"));
        Categoria principales = catalogo.crearCategoria(new Categoria("Platos fuertes", "Platos principales"));
        Categoria bebidas = catalogo.crearCategoria(new Categoria("Bebidas", "Bebidas frías y calientes"));

        catalogo.crearProducto(new Producto("Empanadas", 6000, entradas.getId()));
        catalogo.crearProducto(new Producto("Nachos con queso", 12000, entradas.getId()));
        catalogo.crearProducto(new Producto("Hamburguesa clásica", 22000, principales.getId()));
        catalogo.crearProducto(new Producto("Pizza personal", 25000, principales.getId()));
        catalogo.crearProducto(new Producto("Limonada natural", 7000, bebidas.getId()));
        catalogo.crearProducto(new Producto("Gaseosa", 5000, bebidas.getId()));

        mesas.crear(new Mesa(1, 4));
        mesas.crear(new Mesa(2, 2));
        mesas.crear(new Mesa(3, 6));

        usuarios.registrar(new Mesero("Ana Torres", "mesero", "1234"));
        usuarios.registrar(new Cocina("Luis Gómez", "cocina", "1234"));
        usuarios.registrar(new Domiciliario("Sofía Díaz", "domiciliario", "1234", "3001234567"));
        usuarios.registrar(new Administrador("Carlos Ruiz", "admin", "1234"));
    }
}
