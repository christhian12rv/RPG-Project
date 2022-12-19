package service;

import entitys.Monstro;
import entitys.Partida;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PartidaService {
    private EntityManager entityManager;

    public PartidaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Partida partida) {
        entityManager.getTransaction().begin();
        entityManager.persist(partida);
        entityManager.getTransaction().commit();
    }

    public List<Partida> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Partida m");
        List<Partida> partidas = query.getResultList();

        return partidas;
    }

    public Partida findById(Integer id) {
        Partida partida = entityManager.find(Partida.class, id);

        return partida;
    }

    public int deleteById(Integer id) {
        Partida partida = entityManager.find(Partida.class, id);

        if (partida == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(partida);
        entityManager.getTransaction().commit();
        return 1;
    }
}