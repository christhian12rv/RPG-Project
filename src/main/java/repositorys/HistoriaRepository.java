package repositorys;

import entitys.Historia;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class HistoriaRepository extends BaseRepository<Historia> {

    public HistoriaRepository(EntityManager entityManager) {
        super(Historia.class, entityManager);
    }

    public List<Historia> findAllRandom() {
        Query query = this.getEntityManager().createQuery("SELECT m FROM Historia m " +
            "ORDER BY RANDOM()");
        List<Historia> historias = query.getResultList();

        return historias;
    }
}