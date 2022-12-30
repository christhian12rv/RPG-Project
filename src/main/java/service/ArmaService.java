package service;

import entitys.Arma;
import enums.TipoAtributo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArmaService {
    private EntityManager entityManager;

    public ArmaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Arma arma) {
        entityManager.getTransaction().begin();
        entityManager.persist(arma);
        entityManager.getTransaction().commit();
    }

    public List<Arma> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Arma m");
        List<Arma> armas = query.getResultList();

        return armas;
    }

    public Arma findById(Integer id) {
        Arma arma = entityManager.find(Arma.class, id);

        return arma;
    }

    public Arma findByRaridadeAndByTipoAtributo(TipoAtributo tipoAtributo) {
        Query query = entityManager.createQuery("SELECT m FROM Arma m " +
            "WHERE raridade = 'COMUM' " +
            "AND tipoAtributo = '" + tipoAtributo + "' " +
            "ORDER BY RANDOM()");
        Arma arma = (Arma) query.getSingleResult();

        return arma;
    }

    public int deleteById(Integer id) {
        Arma arma = entityManager.find(Arma.class, id);

        if (arma == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(arma);
        entityManager.getTransaction().commit();
        return 1;
    }
}