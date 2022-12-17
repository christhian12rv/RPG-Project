package service;

import entitys.Habilidade;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class HabilidadeService {
    private EntityManager entityManager;

    public HabilidadeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Habilidade habilidade) {
        entityManager.getTransaction().begin();
        entityManager.persist(habilidade);
        entityManager.getTransaction().commit();
    }

    public List<Habilidade> findAll() {
        Query query = entityManager.createQuery("SELECT h FROM Habilidade h");
        List<Habilidade> habilidades = query.getResultList();

        return habilidades;
    }

    public Habilidade findById(Integer id) {
        Habilidade habilidade = entityManager.find(Habilidade.class, id);

        return habilidade;
    }

    public int deleteById(Integer id) {
        Habilidade habilidade = entityManager.find(Habilidade.class, id);

        if (habilidade == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(habilidade);
        entityManager.getTransaction().commit();
        return 1;
    }
}