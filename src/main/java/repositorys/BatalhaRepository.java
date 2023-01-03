package repositorys;

import entitys.Batalha;
import entitys.Historia;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BatalhaRepository extends BaseRepository<Batalha> {

    public BatalhaRepository(EntityManager entityManager) {
        super(Batalha.class, entityManager);
    }

}