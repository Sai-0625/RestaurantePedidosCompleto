package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Mesa;
public class MesaDAOImpl extends GenericDAOImpl<Mesa> implements MesaDAO { public Mesa buscarPorNumero(int numero){for(Mesa m:datos)if(m.getNumero()==numero)return m;return null;} }
