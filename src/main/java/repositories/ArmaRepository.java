package repositories;

import enums.RaridadeArma;
import enums.TipoAtributo;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Arma;

import java.util.List;
import java.util.stream.Collectors;

public class ArmaRepository extends BaseRepository<Arma> {
    
    public ArmaRepository(EntityManager entityManager) {
        super(Arma.class, entityManager);
    }

    public Arma findOneRandomByRaridadeTipoAtributo(RaridadeArma raridadeArma, List<TipoAtributo> tipoAtributos) {
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

    public Arma findOneByRaridade(RaridadeArma raridadeArma) {
        Query query = this.getEntityManager().createQuery("SELECT m FROM Arma m " +
            "WHERE raridade = '" + raridadeArma + "' " +
            "ORDER BY RANDOM()");
        Arma arma = (Arma) query.setMaxResults(1).getSingleResult();
        return arma;
    }

}