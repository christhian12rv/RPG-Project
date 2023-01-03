package repositorys;

import entitys.Partida;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PartidaRepository extends BaseRepository<Partida>{

    public PartidaRepository(EntityManager entityManager) {
        super(Partida.class, entityManager);
    }
}