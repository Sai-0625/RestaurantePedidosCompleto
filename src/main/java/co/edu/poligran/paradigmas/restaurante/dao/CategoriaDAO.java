package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Categoria;
public interface CategoriaDAO extends GenericDAO<Categoria> { Categoria buscarPorNombre(String nombre); }
