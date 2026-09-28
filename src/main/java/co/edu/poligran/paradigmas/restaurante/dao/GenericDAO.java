package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Identificable;
import java.util.List;

/** Operaciones CRUD comunes a todas las entidades en memoria. */
public interface GenericDAO<T extends Identificable> {
    T crear(T entidad); T buscarPorId(int id); List<T> listar(); T actualizar(T entidad); boolean eliminar(int id);
}
