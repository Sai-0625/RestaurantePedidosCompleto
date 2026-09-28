package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Mesa;
public interface MesaDAO extends GenericDAO<Mesa> { Mesa buscarPorNumero(int numero); }
