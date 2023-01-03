package utils;

import config.AtributosIniciais;
import entitys.Arma;
import entitys.Inventario;
import entitys.Jogador;
import entitys.Personagem;
import enums.TipoAtributo;
import repositorys.ArmaRepository;
import repositorys.JogadorRepository;
import services.InventarioService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogadorUtil {
    PersonagemUtil personagemUtil;
    InventarioService inventarioService;
    ArmaRepository armaRepository;
    static JogadorRepository jogadorRepository;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        JogadorRepository jRepository = new JogadorRepository(entityManager);
        List<Jogador> jogadores = jRepository.findAll();
        System.out.println("");
        entityManager.close();
        JPAUtil.shutdown();
    }

    public static List<Jogador> getJogadores() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        System.out.println("");
        return null;
    }

    public List<Jogador> criarJogadores(int qtdJogadores) {
        Scanner scanner = new Scanner(System.in);

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

        arma = armaRepository.findOneByRaridadeAndTipoAtributo(tipoAtributo, "COMUM");
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

    public ArmaRepository getArmaRepository() {
        return armaRepository;
    }

    public void setArmaRepository(ArmaRepository armaRepository) {
        this.armaRepository = armaRepository;
    }

    public JogadorRepository getJogadorRepository() {
        return jogadorRepository;
    }

    public void setJogadorRepository(JogadorRepository jogadorRepository) {
        this.jogadorRepository = jogadorRepository;
    }
}