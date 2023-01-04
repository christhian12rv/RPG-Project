package repositorys;

import entitys.Arma;
import entitys.Arma;
import enums.RaridadeArma;
import enums.TipoAtributo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class ArmaRepository extends BaseRepository<Arma> {
    
    public ArmaRepository(EntityManager entityManager) {
        super(Arma.class, entityManager);
    }

    public Arma findAllRandomByTipoAtributo(RaridadeArma raridadeArma, List<TipoAtributo> tipoAtributos) {
        String whereTipoAtributos = "AND a.tipoAtributo = '" + tipoAtributos.get(0) + "' ";
        tipoAtributos.remove(0);

        for (TipoAtributo tipoAtributo: tipoAtributos) {
            whereTipoAtributos += "OR a.tipoAtributo = '" + tipoAtributo + "' ";
        }

        Query query = this.getEntityManager().createQuery("SELECT a FROM Arma a " +
                "WHERE raridade = '" + raridadeArma + "' " +
                whereTipoAtributos +
                "ORDER BY RANDOM() ");
        Arma armas = (Arma) query.setMaxResults(1).getSingleResult();

        return armas;
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