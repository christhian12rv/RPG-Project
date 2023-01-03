package repositorys;

import entitys.MiniHistoria;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class MiniHistoriaRepository extends BaseRepository<MiniHistoria>{

    public MiniHistoriaRepository(EntityManager entityManager) {
        super(MiniHistoria.class, entityManager);
    }

}