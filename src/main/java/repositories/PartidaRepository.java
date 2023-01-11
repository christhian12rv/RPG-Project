package repositories;

import javax.persistence.EntityManager;

import entities.Partida;

public class PartidaRepository extends BaseRepository<Partida>{

    public PartidaRepository(EntityManager entityManager) {
        super(Partida.class, entityManager);
    }
}