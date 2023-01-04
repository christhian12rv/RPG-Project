package utils;

import entitys.*;
import repositorys.BatalhaRepository;
import repositorys.MonstroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BatalhaUtil {
    MonstroRepository monstroRepository;
    BatalhaRepository batalhaRepository;

    public Batalha criarBatalha(Historia historia, MiniHistoria miniHistoria, List<Jogador> jogadores) {
        int qtdJogadores = jogadores.size();
        int i = 0, ini = 0;
        boolean inseriu = false;
        List<Monstro> monstros = new ArrayList<>();
        List<Personagem> personagens = new ArrayList<>(jogadores);
        List<Personagem> iniciativa = new ArrayList<>();
        List<Integer> valoresIniciativa = new ArrayList<>();

        for (i = 0; i < qtdJogadores; i++) {
            monstros.add(monstroRepository.findOneRandomByDificuldadeAndTipo(miniHistoria.getDificuldade(), historia.getTipoMonstros()));   
        }

        personagens.addAll(monstros);

        for (Personagem personagem : personagens) {
            Random random = new Random();
            ini =  random.nextInt(20) + 1;

            if (iniciativa.size() <= 0) {
                valoresIniciativa.add(ini);
                iniciativa.add(personagem);
                continue;
            }

            inseriu = false;
            for (i = 0; i < iniciativa.size(); i++) {
                if (valoresIniciativa.get(i) > ini) {
                    valoresIniciativa.add(i, ini);
                    iniciativa.add(i, personagem);
                    inseriu = true;
                    break;
                }
            }

            if (!inseriu) {
                valoresIniciativa.add(ini);
                iniciativa.add(personagem);
            }
        }

        Batalha batalha = new Batalha(miniHistoria, monstros, iniciativa);
        batalhaRepository.save(batalha);
        return batalha;
    }

    public MonstroRepository getMonstroRepository() {
        return monstroRepository;
    }

    public void setMonstroRepository(MonstroRepository monstroRepository) {
        this.monstroRepository = monstroRepository;
    }

    public BatalhaRepository getBatalhaRepository() {
        return batalhaRepository;
    }

    public void setBatalhaRepository(BatalhaRepository batalhaRepository) {
        this.batalhaRepository = batalhaRepository;
    }
}