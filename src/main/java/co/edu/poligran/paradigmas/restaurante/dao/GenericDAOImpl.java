package co.edu.poligran.paradigmas.restaurante.dao;
import co.edu.poligran.paradigmas.restaurante.modelo.Identificable;
import java.util.ArrayList; import java.util.Collections; import java.util.List;

/** Implementación reutilizable de un DAO en memoria. */
public class GenericDAOImpl<T extends Identificable> implements GenericDAO<T> {
    protected final List<T> datos=new ArrayList<>();
    public T crear(T entidad){if(entidad==null)throw new IllegalArgumentException("La entidad es obligatoria");if(entidad.getId()<=0)entidad.setId(siguienteId());else if(buscarPorId(entidad.getId())!=null)throw new IllegalArgumentException("Id repetido");datos.add(entidad);return entidad;}
    public T buscarPorId(int id){for(T entidad:datos)if(entidad.getId()==id)return entidad;return null;}
    public List<T> listar(){return Collections.unmodifiableList(datos);}
    public T actualizar(T entidad){if(entidad==null)throw new IllegalArgumentException("La entidad es obligatoria");for(int i=0;i<datos.size();i++)if(datos.get(i).getId()==entidad.getId()){datos.set(i,entidad);return entidad;}return null;}
    public boolean eliminar(int id){return datos.removeIf(entidad->entidad.getId()==id);}
    private int siguienteId(){int max=0;for(T entidad:datos)max=Math.max(max,entidad.getId());return max+1;}
}
