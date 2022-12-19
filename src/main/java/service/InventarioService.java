package service;

import entitys.Inventario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class InventarioService {
    private EntityManager entityManager;

    public InventarioService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Inventario inventario) {
        entityManager.getTransaction().begin();
        entityManager.persist(inventario);
        entityManager.getTransaction().commit();
    }

    public List<Inventario> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Inventario m");
        List<Inventario> inventarios = query.getResultList();

        return inventarios;
    }

    public Inventario findById(Integer id) {
        Inventario inventario = entityManager.find(Inventario.class, id);

        return inventario;
    }

    public int deleteById(Integer id) {
        Inventario inventario = entityManager.find(Inventario.class, id);

        if (inventario == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(inventario);
        entityManager.getTransaction().commit();
        return 1;
    }
}