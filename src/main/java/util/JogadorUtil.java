package util;

import entitys.Jogador;

public class JogadorUtil {
    public List<Jogador> criarJogadores(int qtdJogadores) {
        List<Jogador> jogadores = new ArrayList<>();
        for (i = 0; i < qtdJogadores; i++) {
            System.out.println("Jogador " + (i + 1));
            jogadores.add(criarJogador());
        }
        return jogadores;
    }

    private Jogador criarJogador() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Nome")
    }

    
}