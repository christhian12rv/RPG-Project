package repositories;

import javax.persistence.EntityManager;

import entities.Batalha;

public class BatalhaRepository extends BaseRepository<Batalha> {

    public BatalhaRepository(EntityManager entityManager) {
        super(Batalha.class, entityManager);
    }

}