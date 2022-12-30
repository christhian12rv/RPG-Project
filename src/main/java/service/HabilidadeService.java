package service;

import entitys.Habilidade;
import enums.TipoAtributo;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class HabilidadeService {
    private EntityManager entityManager;

    public HabilidadeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Habilidade habilidade) {
        entityManager.getTransaction().begin();
        entityManager.persist(habilidade);
        entityManager.getTransaction().commit();
    }

    public List<Habilidade> findAll() {
        Query query = entityManager.createQuery("SELECT h FROM Habilidade h");
        List<Habilidade> habilidades = query.getResultList();

        return habilidades;
    }

    public List<Habilidade> findAllRandomByPreRequisitosAndTipo(int forca, int destreza, int sabedoria, int defesa, List<TipoAtributo> tipoAtributos) {
        String whereTipoAtributos = "AND h.tipo = '" + tipoAtributos.get(0) + "' ";
        tipoAtributos.remove(0);

        for (TipoAtributo tipoAtributo: tipoAtributos) {
            whereTipoAtributos += "OR h.tipo = '" + tipoAtributo + "' ";
        }

        Query query = entityManager.createQuery("SELECT h FROM Habilidade h " +
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

    public Habilidade findById(Integer id) {
        Habilidade habilidade = entityManager.find(Habilidade.class, id);

        return habilidade;
    }

    public int deleteById(Integer id) {
        Habilidade habilidade = entityManager.find(Habilidade.class, id);

        if (habilidade == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(habilidade);
        entityManager.getTransaction().commit();
        return 1;
    }
}