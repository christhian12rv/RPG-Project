package repositories;

import javax.persistence.EntityManager;

import entities.MiniHistoria;

public class MiniHistoriaRepository extends BaseRepository<MiniHistoria>{

    public MiniHistoriaRepository(EntityManager entityManager) {
        super(MiniHistoria.class, entityManager);
    }

}