package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Inventario;

import java.util.List;

public class InventarioRepository extends BaseRepository<Inventario> {
    
    public InventarioRepository(EntityManager entityManager) {
        super(Inventario.class, entityManager);
    }

}