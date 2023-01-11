package utils.EntitiesUtils;

import config.AtributosIniciais;
import entities.Arma;
import entities.Inventario;
import entities.Jogador;
import entities.Personagem;
import enums.RaridadeArma;
import repositories.JogadorRepository;
import services.ArmaService;
import services.InventarioService;
import utils.PrintUtil;
import utils.ScannerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogadorUtil {
    private PersonagemUtil personagemUtil;
    private InventarioService inventarioService;
    private ArmaService armaService;
    private JogadorRepository jogadorRepository;
    private PrintUtil printUtil;
    private ScannerUtil scannerUtil;

    public List<Jogador> criarJogadores() {
        Scanner scanner = new Scanner(System.in);
        int qtdJogadores = 0;

        System.out.print("Digite a quantidade de jogadores (entre 1 e 4): ");
        qtdJogadores = scannerUtil.getInt(scanner, 1, 4);

        List<Jogador> jogadores = new ArrayList<>();
        int i = 0;

        for (i = 0; i < qtdJogadores; i++) {
            printUtil.clearTerminal();
            System.out.println("Jogador " + (i + 1));
            jogadores.add(criarJogador(jogadores));
        }
        return jogadores;
    }

    private Jogador criarJogador(List<Jogador> jogadores) {
        Personagem personagem = personagemUtil.criarPersonagemJogador(jogadores);
        int mana = AtributosIniciais.MANA + (personagem.getSabedoria() * AtributosIniciais.MULTIPLICADOR_MANA);
        int manaMaxima = mana;
        Arma arma = null;
        Inventario inventario = null;

        arma = armaService.findArmaByRaridadeAndTipoAtributo(RaridadeArma.COMUM, personagem.getForca(), personagem.getDestreza(), personagem.getSabedoria(), personagem.getDefesa());
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

    public PrintUtil getPrintUtil() {
        return printUtil;
    }

    public void setPrintUtil(PrintUtil printUtil) {
        this.printUtil = printUtil;
    }

    public ScannerUtil getScannerUtil() {
        return scannerUtil;
    }

    public void setScannerUtil(ScannerUtil scannerUtil) {
        this.scannerUtil = scannerUtil;
    }
}