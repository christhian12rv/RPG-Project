package service;

import entitys.Monstro;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MonstroService {
    private EntityManager entityManager;

    public MonstroService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Monstro monstro) {
        entityManager.getTransaction().begin();
        entityManager.persist(monstro);
        entityManager.getTransaction().commit();
    }

    public List<Monstro> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Monstro m");
        List<Monstro> monstros = query.getResultList();

        return monstros;
    }

    public Monstro findById(Integer id) {
        Monstro monstro = entityManager.find(Monstro.class, id);

        return monstro;
    }

    public int deleteById(Integer id) {
        Monstro monstro = entityManager.find(Monstro.class, id);

        if (monstro == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(monstro);
        entityManager.getTransaction().commit();
        return 1;
    }
}