package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Batalha;
import entities.Historia;

import java.util.List;

public class BatalhaRepository extends BaseRepository<Batalha> {

    public BatalhaRepository(EntityManager entityManager) {
        super(Batalha.class, entityManager);
    }

}