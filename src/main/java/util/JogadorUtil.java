package util;

import entitys.Arma;
import entitys.Inventario;
import entitys.Jogador;
import entitys.Personagem;
import enums.TipoAtributo;
import service.ArmaService;
import service.JogadorService;

import java.util.ArrayList;
import java.util.List;

public class JogadorUtil {
    PersonagemUtil personagemUtil;
    InventarioUtil inventarioUtil;
    ArmaService armaService;
    JogadorService jogadorService;

    public List<Jogador> criarJogadores(int qtdJogadores) {
        List<Jogador> jogadores = new ArrayList<>();
        int i = 0;

        for (i = 0; i < qtdJogadores; i++) {
            System.out.println("Jogador " + (i + 1));
            jogadores.add(criarJogador());
        }
        return jogadores;
    }

    private Jogador criarJogador() {
        Personagem personagem = personagemUtil.criarPersonagem();
        int mana = 10 + (personagem.getSabedoria() * 3);
        int manaMaxima = mana;
        Arma arma = null;
        Inventario inventario = null;
        TipoAtributo tipoAtributo = TipoAtributo.FORCA;

        if (personagem.getDestreza() > personagem.getForca())
            tipoAtributo = TipoAtributo.DESTREZA;
        
        if (personagem.getSabedoria() > personagem.getDestreza())
            tipoAtributo = TipoAtributo.SABEDORIA;

        arma = armaService.findByRaridadeAndByTipoAtributo(tipoAtributo);
        inventario = inventarioUtil.criarInventarioInicial();

        Jogador jogador = new Jogador(personagem, mana, manaMaxima, arma, inventario);
        jogadorService.save(jogador);
        return jogador;
    }

    public PersonagemUtil getPersonagemUtil() {
        return personagemUtil;
    }

    public void setPersonagemUtil(PersonagemUtil personagemUtil) {
        this.personagemUtil = personagemUtil;
    }

    public InventarioUtil getInventarioUtil() {
        return inventarioUtil;
    }

    public void setInventarioUtil(InventarioUtil inventarioUtil) {
        this.inventarioUtil = inventarioUtil;
    }

    public ArmaService getArmaService() {
        return armaService;
    }

    public void setArmaService(ArmaService armaService) {
        this.armaService = armaService;
    }

    public JogadorService getJogadorService() {
        return jogadorService;
    }

    public void setJogadorService(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }
}