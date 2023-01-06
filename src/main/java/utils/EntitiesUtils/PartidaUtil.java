package utils.EntitiesUtils;

import java.util.ArrayList;
import java.util.List;

import entities.*;
import repositories.PartidaRepository;

public class PartidaUtil {
    private PartidaRepository partidaRepository;
    private BatalhaUtil batalhaUtil = new BatalhaUtil();

    public Partida criarPartida(List<Jogador> jogadores, Historia historia) {
        List<Batalha> batalhas = new ArrayList<>();
        
        for (MiniHistoria miniHistoria: historia.getMiniHistorias()) {
            batalhas.add(batalhaUtil.criarBatalha(historia, miniHistoria, jogadores));
        }

        Partida partida = new Partida(historia, batalhas, jogadores);
        partidaRepository.save(partida);

        return partida;
    }

    public PartidaRepository getPartidaRepository() {
        return partidaRepository;
    }

    public void setPartidaRepository(PartidaRepository partidaRepository) {
        this.partidaRepository = partidaRepository;
    }

    public BatalhaUtil getBatalhaUtil() {
        return batalhaUtil;
    }

    public void setBatalhaUtil(BatalhaUtil batalhaUtil) {
        this.batalhaUtil = batalhaUtil;
    }
}