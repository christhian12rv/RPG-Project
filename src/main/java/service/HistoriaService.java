package service;

import entitys.Historia;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class HistoriaService {
    private EntityManager entityManager;

    public HistoriaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Historia historia) {
        entityManager.getTransaction().begin();
        entityManager.persist(historia);
        entityManager.getTransaction().commit();
    }

    public List<Historia> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Historia m");
        List<Historia> historias = query.getResultList();

        return historias;
    }

    public Historia findById(Integer id) {
        Historia historia = entityManager.find(Historia.class, id);

        return historia;
    }

    public int deleteById(Integer id) {
        Historia historia = entityManager.find(Historia.class, id);

        if (historia == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(historia);
        entityManager.getTransaction().commit();
        return 1;
    }
}