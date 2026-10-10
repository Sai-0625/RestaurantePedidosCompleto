package co.edu.poligran.paradigmas.restaurante.negocio;

import co.edu.poligran.paradigmas.restaurante.modelo.Mesa;
import java.util.List;

/** Gestiona el CRUD y la disponibilidad de las mesas. */
public class MesasManager {
    private final DatosRestaurante datos;
    public MesasManager(DatosRestaurante datos) { this.datos = datos; }
    public Mesa crear(Mesa mesa) {
        if (mesa.getCapacidad() <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        if (datos.mesas.buscarPorNumero(mesa.getNumero()) != null) throw new IllegalArgumentException("El número de mesa ya existe");
        return datos.mesas.crear(mesa);
    }
    public List<Mesa> listar() { return datos.mesas.listar(); }
    public boolean actualizar(Mesa mesa) { return datos.mesas.actualizar(mesa) != null; }
    public boolean eliminar(int id) { Mesa mesa=datos.mesas.buscarPorId(id); if(mesa!=null && mesa.isOcupada()) throw new IllegalStateException("La mesa está ocupada"); return datos.mesas.eliminar(id); }
}
