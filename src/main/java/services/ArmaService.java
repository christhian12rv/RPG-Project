package services;

import enums.RaridadeArma;
import enums.TipoAtributo;
import repositories.ArmaRepository;

import java.util.ArrayList;
import java.util.List;

import entities.Arma;

public class ArmaService {
    ArmaRepository armaRepository;

    public Arma findArmasByRaridadeAndTipoAtributo(RaridadeArma raridadeArma, int forca, int destreza, int sabedoria, int defesa) {
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

        Arma arma = armaRepository.findAllRandomByTipoAtributo(raridadeArma, tipoAtributos);

        return arma;
    }

    public ArmaRepository getArmaRepository() {
        return armaRepository;
    }

    public void setArmaRepository(ArmaRepository armaRepository) {
        this.armaRepository = armaRepository;
    }
}
