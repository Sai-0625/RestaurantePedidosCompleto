package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.Categoria;
import co.edu.poligran.paradigmas.restaurante.modelo.Producto;
import java.util.List;

/** Gestiona categorías y productos del menú. */
public class CatalogoManager {
    private final DatosRestaurante datos;
    public CatalogoManager(DatosRestaurante datos) { this.datos = datos; }
    public Categoria crearCategoria(Categoria categoria) { return datos.categorias.crear(categoria); }
    public List<Categoria> listarCategorias() { return datos.categorias.listar(); }
    public boolean actualizarCategoria(Categoria categoria) { return datos.categorias.actualizar(categoria) != null; }
    public boolean eliminarCategoria(int id) {
        if (!datos.productos.listarPorCategoria(id).isEmpty()) throw new IllegalStateException("La categoría tiene productos asociados");
        return datos.categorias.eliminar(id);
    }
    public Producto crearProducto(Producto producto) { validarProducto(producto); return datos.productos.crear(producto); }
    public List<Producto> listarProductos() { return datos.productos.listar(); }
    public List<Producto> listarPorCategoria(int idCategoria) { return datos.productos.listarPorCategoria(idCategoria); }
    public boolean actualizarProducto(Producto producto) { validarProducto(producto); return datos.productos.actualizar(producto) != null; }
    public boolean eliminarProducto(int id) { return datos.productos.eliminar(id); }
    private void validarProducto(Producto producto) {
        if (producto == null || datos.categorias.buscarPorId(producto.getIdCategoria()) == null) throw new IllegalArgumentException("La categoría del producto no existe");
        if (producto.getPrecio() < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
    }
}
