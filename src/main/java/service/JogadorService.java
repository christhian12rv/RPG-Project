package service;

import entitys.Jogador;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class JogadorService {
    private EntityManager entityManager;

    public JogadorService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Jogador jogador) {
        entityManager.getTransaction().begin();
        entityManager.persist(jogador);
        entityManager.getTransaction().commit();
    }

    public List<Jogador> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Jogador m");
        List<Jogador> jogadores = query.getResultList();

        return jogadores;
    }

    public Jogador findById(Integer id) {
        Jogador jogador = entityManager.find(Jogador.class, id);

        return jogador;
    }

    public int deleteById(Integer id) {
        Jogador jogador = entityManager.find(Jogador.class, id);

        if (jogador == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(jogador);
        entityManager.getTransaction().commit();
        return 1;
    }
}