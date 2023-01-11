package repositories;

import javax.persistence.EntityManager;

import entities.Inventario;

public class InventarioRepository extends BaseRepository<Inventario> {
    
    public InventarioRepository(EntityManager entityManager) {
        super(Inventario.class, entityManager);
    }

}