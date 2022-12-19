package service;

import entitys.Monstro;
import entitys.Partida;
import entitys.Personagem;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PersonagemService {
    private EntityManager entityManager;

    public PersonagemService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Personagem personagem) {
        entityManager.getTransaction().begin();
        entityManager.persist(personagem);
        entityManager.getTransaction().commit();
    }

    public List<Personagem> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Personagem m");
        List<Personagem> personagens = query.getResultList();

        return personagens;
    }

    public Personagem findById(Integer id) {
        Personagem personagem = entityManager.find(Personagem.class, id);

        return personagem;
    }

    public int deleteById(Integer id) {
        Personagem personagem = entityManager.find(Personagem.class, id);

        if (personagem == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(personagem);
        entityManager.getTransaction().commit();
        return 1;
    }
}