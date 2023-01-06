package services;

import enums.TipoAtributo;
import repositories.HabilidadeRepository;

import java.util.ArrayList;
import java.util.List;

import entities.Habilidade;

public class HabilidadeService {
    HabilidadeRepository habilidadeRepository;

    public List<Habilidade> findHabilidadesByPreRequisitos(int forca, int destreza, int sabedoria, int defesa) {
        int atributoMaiorValor = forca;
        TipoAtributo tipoAtributoMaiorValor = TipoAtributo.FORCA;
        List<TipoAtributo> tipoAtributos = new ArrayList<>();

        if (destreza > atributoMaiorValor) {
            tipoAtributoMaiorValor = TipoAtributo.DESTREZA;
            atributoMaiorValor = destreza;
        }

        if (sabedoria > atributoMaiorValor) {
            tipoAtributoMaiorValor = TipoAtributo.SABEDORIA;
            atributoMaiorValor = sabedoria;
        }

        if (defesa > atributoMaiorValor) {
            tipoAtributoMaiorValor = TipoAtributo.DEFESA;
            atributoMaiorValor = defesa;
        }

        tipoAtributos.add(tipoAtributoMaiorValor);
        if (forca == atributoMaiorValor && tipoAtributoMaiorValor != TipoAtributo.FORCA) {
            tipoAtributos.add(TipoAtributo.FORCA);
        }
        if (destreza == atributoMaiorValor && tipoAtributoMaiorValor != TipoAtributo.DESTREZA) {
            tipoAtributos.add(TipoAtributo.DESTREZA);
        }

        if (sabedoria == atributoMaiorValor && tipoAtributoMaiorValor != TipoAtributo.SABEDORIA) {
            tipoAtributos.add(TipoAtributo.SABEDORIA);
        }
        if (defesa == atributoMaiorValor && tipoAtributoMaiorValor != TipoAtributo.DEFESA) {
            tipoAtributos.add(TipoAtributo.DEFESA);
        }

        List<Habilidade> habilidades = habilidadeRepository.findAllRandomByPreRequisitosAndTipo(forca, destreza, sabedoria, defesa, tipoAtributos).subList(0, 3);

        return habilidades;
    }

    public HabilidadeRepository getHabilidadeRepository() {
        return habilidadeRepository;
    }

    public void setHabilidadeRepository(HabilidadeRepository habilidadeRepository) {
        this.habilidadeRepository = habilidadeRepository;
    }
}
