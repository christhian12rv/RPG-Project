package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Partida;

import java.util.List;

public class PartidaRepository extends BaseRepository<Partida>{

    public PartidaRepository(EntityManager entityManager) {
        super(Partida.class, entityManager);
    }
}