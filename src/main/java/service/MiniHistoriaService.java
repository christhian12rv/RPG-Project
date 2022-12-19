package service;

import entitys.MiniHistoria;
import entitys.Monstro;
import entitys.Partida;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MiniHistoriaService {
    private EntityManager entityManager;

    public MiniHistoriaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(MiniHistoria miniHistoria) {
        entityManager.getTransaction().begin();
        entityManager.persist(miniHistoria);
        entityManager.getTransaction().commit();
    }

    public List<MiniHistoria> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM MiniHistoria m");
        List<MiniHistoria> miniHistorias = query.getResultList();

        return miniHistorias;
    }

    public MiniHistoria findById(Integer id) {
        MiniHistoria miniHistoria = entityManager.find(MiniHistoria.class, id);

        return miniHistoria;
    }

    public int deleteById(Integer id) {
        MiniHistoria miniHistoria = entityManager.find(MiniHistoria.class, id);

        if (miniHistoria == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(miniHistoria);
        entityManager.getTransaction().commit();
        return 1;
    }
}