package repositories;

import enums.DificuldadeMonstro;
import enums.TipoMonstro;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Monstro;

public class MonstroRepository extends BaseRepository<Monstro>{

    public MonstroRepository(EntityManager entityManager) {
        super(Monstro.class, entityManager);
    }

    public Monstro findOneRandomByDificuldadeAndTipo(DificuldadeMonstro dificuldade, TipoMonstro tipo) {
        Query query = this.getEntityManager().createQuery("SELECT m FROM Monstro m " +
            "WHERE dificuldade = '" + dificuldade + "' " +
            "AND tipo = '" + tipo + "' " +
            "ORDER BY RANDOM()");
        Monstro monstro = (Monstro) query.setMaxResults(1).getSingleResult();

        return monstro;
    }

}