package repositorys;

import entitys.Habilidade;
import enums.TipoAtributo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class HabilidadeRepository extends BaseRepository<Habilidade> {
    
    public HabilidadeRepository(EntityManager entityManager) {
        super(Habilidade.class, entityManager);
    }

    public List<Habilidade> findAllRandomByPreRequisitosAndTipo(int forca, int destreza, int sabedoria, int defesa, List<TipoAtributo> tipoAtributos) {
        String whereTipoAtributos = "AND h.tipo = '" + tipoAtributos.get(0) + "' ";
        tipoAtributos.remove(0);

        for (TipoAtributo tipoAtributo: tipoAtributos) {
            whereTipoAtributos += "OR h.tipo = '" + tipoAtributo + "' ";
        }

        Query query = this.getEntityManager().createQuery("SELECT h FROM Habilidade h " +
            "WHERE h.dropavel = true " +
            whereTipoAtributos +
            "ORDER BY RANDOM()");
        List<Habilidade> habilidades = query.getResultList();

        habilidades = habilidades.stream().filter(h -> {
            if (forca < h.getPreRequisitos().get(0))
                return false;
            if (destreza < h.getPreRequisitos().get(1))
                return false;
            if (sabedoria < h.getPreRequisitos().get(2))
                return false;
            if (defesa < h.getPreRequisitos().get(3))
                return false;

            return true;
        }).collect(Collectors.toList());

        return habilidades;
    }

}