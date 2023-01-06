package utils.EntitiesUtils;

import config.AtributosIniciais;
import entities.Arma;
import entities.Inventario;
import entities.Jogador;
import entities.Personagem;
import enums.RaridadeArma;
import enums.TipoAtributo;
import repositories.JogadorRepository;
import services.ArmaService;
import services.InventarioService;
import utils.JPAUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogadorUtil {
    private PersonagemUtil personagemUtil;
    private InventarioService inventarioService;
    private ArmaService armaService;
    private JogadorRepository jogadorRepository;

    public List<Jogador> criarJogadores() {
        Scanner scanner = new Scanner(System.in);
        int qtdJogadores = 0;

        System.out.print("Digite a quantidade de jogadores (entre 1 e 4): ");
        qtdJogadores = scanner.nextInt();

        while (qtdJogadores <= 0 || qtdJogadores > 4) {
            System.out.print("Quantidade inválida (entre 1 e 4). Digite novamente: ");
            qtdJogadores = scanner.nextInt();
        }

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
        int mana = AtributosIniciais.MANA + (personagem.getSabedoria() * AtributosIniciais.MULTIPLICADOR_MANA);
        int manaMaxima = mana;
        Arma arma = null;
        Inventario inventario = null;
        TipoAtributo tipoAtributo = TipoAtributo.FORCA;

        if (personagem.getDestreza() > personagem.getForca())
            tipoAtributo = TipoAtributo.DESTREZA;
        
        if (personagem.getSabedoria() > personagem.getDestreza())
            tipoAtributo = TipoAtributo.SABEDORIA;

        arma = armaService.findArmasByRaridadeAndTipoAtributo(RaridadeArma.COMUM, personagem.getForca(), personagem.getDestreza(), personagem.getSabedoria(), personagem.getDefesa());
        inventario = inventarioService.criarInventarioInicial();

        Jogador jogador = new Jogador(personagem, mana, manaMaxima, arma, inventario);
        jogadorRepository.save(jogador);
        return jogador;
    }

    public PersonagemUtil getPersonagemUtil() {
        return personagemUtil;
    }

    public void setPersonagemUtil(PersonagemUtil personagemUtil) {
        this.personagemUtil = personagemUtil;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public ArmaService getArmaService() {
        return armaService;
    }

    public void setArmaService(ArmaService armaService) {
        this.armaService = armaService;
    }

    public JogadorRepository getJogadorRepository() {
        return jogadorRepository;
    }

    public void setJogadorRepository(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }
}