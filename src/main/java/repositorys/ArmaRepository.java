package repositorys;

import entitys.Arma;
import enums.TipoAtributo;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArmaRepository extends BaseRepository<Arma> {
    
    public ArmaRepository(EntityManager entityManager) {
        super(Arma.class, entityManager);
    }

    public Arma findOneByRaridadeAndTipoAtributo(TipoAtributo tipoAtributo, RaridadeArma raridadeArma) {
        Query query = this.getEntityManager().createQuery("SELECT m FROM Arma m " +
            "WHERE raridade = '" + raridadeArma + "' " +
            "AND tipoAtributo = '" + tipoAtributo + "' " +
            "ORDER BY RANDOM()");
        Arma arma = (Arma) query.getSingleResult();
        return arma;
    }

}