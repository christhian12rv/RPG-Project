package repositorys;

import entitys.Inventario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class InventarioRepository extends BaseRepository<Inventario> {
    
    public InventarioRepository(EntityManager entityManager) {
        super(Inventario.class, entityManager);
    }

}