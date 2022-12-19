package service;

import entitys.Batalha;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BatalhaService {
    private EntityManager entityManager;

    public BatalhaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Batalha batalha) {
        entityManager.getTransaction().begin();
        entityManager.persist(batalha);
        entityManager.getTransaction().commit();
    }

    public List<Batalha> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Batalha m");
        List<Batalha> batalhas = query.getResultList();

        return batalhas;
    }

    public Batalha findById(Integer id) {
        Batalha batalha = entityManager.find(Batalha.class, id);

        return batalha;
    }

    public int deleteById(Integer id) {
        Batalha batalha = entityManager.find(Batalha.class, id);

        if (batalha == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(batalha);
        entityManager.getTransaction().commit();
        return 1;
    }
}