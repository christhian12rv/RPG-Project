import services.ArmaService;
import services.HabilidadeService;
import services.InventarioService;
import services.ItemService;
import utils.*;
import utils.EntitiesUtils.*;

import javax.persistence.EntityManager;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import entities.Arma;
import entities.Batalha;
import entities.Habilidade;
import entities.Historia;
import entities.Inventario;
import entities.Item;
import entities.Jogador;
import entities.MiniHistoria;
import entities.Monstro;
import entities.Partida;
import entities.Personagem;
import enums.DificuldadeMonstro;
import enums.RaridadeArma;
import enums.TipoAtributo;
import repositories.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    
    private static EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
       
    private static ScannerUtil scannerUtil;
    private static PersonagemUtil personagemUtil;
    private static JogadorUtil jogadorUtil;
    private static HistoriaUtil historiaUtil;
    private static BatalhaUtil batalhaUtil;
    private static PartidaUtil partidaUtil;
    private static PrintUtil printUtil;

    private static ArmaService armaService;
    private static HabilidadeService habilidadeService;
    private static InventarioService inventarioService;
    private static ItemService itemService;

    private static ArmaRepository armaRepository;
    private static BatalhaRepository batalhaRepository;
    private static JogadorRepository jogadorRepository;
    private static HistoriaRepository historiaRepository;
    private static HabilidadeRepository habilidadeRepository;
    private static InventarioRepository inventarioRepository;
    private static ItemRepository itemRepository;
    private static MonstroRepository monstroRepository;
    private static PartidaRepository partidaRepository;

    private static Clip clip;
    private static javax.sound.sampled.AudioInputStream audioIn;
    private static File fClip;

    public static void main(String[] args) {
        // Logger.getRootLogger().setLevel(Level.OFF);
        // Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        playSound("Encounter_with_a_Wizard");

        scanner = new Scanner(System.in);

        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
       
        scannerUtil = new ScannerUtil();
        personagemUtil = new PersonagemUtil();
        jogadorUtil = new JogadorUtil();
        historiaUtil = new HistoriaUtil();
        batalhaUtil = new BatalhaUtil();
        partidaUtil = new PartidaUtil();
        printUtil = new PrintUtil();

        armaService = new ArmaService();
        habilidadeService = new HabilidadeService();
        inventarioService = new InventarioService();
        itemService = new ItemService();

        armaRepository = new ArmaRepository(entityManager);
        batalhaRepository = new BatalhaRepository(entityManager);
        jogadorRepository = new JogadorRepository(entityManager);
        historiaRepository = new HistoriaRepository(entityManager);
        habilidadeRepository = new HabilidadeRepository(entityManager);
        inventarioRepository = new InventarioRepository(entityManager);
        itemRepository = new ItemRepository(entityManager);
        monstroRepository = new MonstroRepository(entityManager);
        partidaRepository = new PartidaRepository(entityManager);

        personagemUtil.setHabilidadeService(habilidadeService);
        jogadorUtil.setPersonagemUtil(personagemUtil);
        jogadorUtil.setArmaService(armaService);
        jogadorUtil.setInventarioService(inventarioService);
        jogadorUtil.setJogadorRepository(jogadorRepository);
        jogadorUtil.setPrintUtil(printUtil);
        jogadorUtil.setScannerUtil(scannerUtil);
        historiaUtil.setHistoriaRepository(historiaRepository);
        batalhaUtil.setMonstroRepository(monstroRepository);
        batalhaUtil.setBatalhaRepository(batalhaRepository);
        partidaUtil.setPartidaRepository(partidaRepository);
        partidaUtil.setBatalhaUtil(batalhaUtil);

        armaService.setArmaRepository(armaRepository);
        habilidadeService.setHabilidadeRepository(habilidadeRepository);
        inventarioService.setInventarioRepository(inventarioRepository);
        inventarioService.setItemService(itemService);
        itemService.setItemRepository(itemRepository);

        List<Jogador> jogadores = null;
        Historia historia = null;
        Partida partida = null;

        printUtil.clearTerminal();
        
        jogadores = jogadorUtil.criarJogadores();
        printUtil.printStringLetraPorLetraSom(15, "Jogadores criados\n\n");

        historia = historiaUtil.historiaRandomica();

        partida = partidaUtil.criarPartida(jogadores, historia);
        printUtil.printStringLetraPorLetraSom(15, "Partida criada! O jogo irá começar...\n\n");

        printUtil.textDelay(2000);
        printUtil.clearTerminal();

        começarPartida(partida);

        entityManager.close();
        JPAUtil.shutdown();
    }

    private static void começarPartida(Partida partida) {

        Boolean opcaoInvalida = false;
        Boolean modoDificil = false;
        int escolha = 0;

        Historia historia = partida.getHistoria();
        List<Batalha> batalhas = partida.getBatalhas();
        List<Jogador> jogadores = partida.getJogadores();

        System.out.print("Em qual modo você deseja jogar?\n[1] Normal\n[2] Difícil\n");
        System.out.print("Digite sua escolha: ");
        escolha = scannerUtil.getInt(scanner, 1, 2);
        if (escolha == 1)
            modoDificil = false;
        else
            modoDificil = true;

        printUtil.clearTerminal();
        
        printUtil.printStringLetraPorLetraSom(15, historia.getDescricao() + "\n\n");
        printUtil.textDelay(2000);

        printarJogadores(jogadores);

        List<Jogador> jogadoresDerrotados = new ArrayList<>();
        for (int z = 0; z < batalhas.size(); z++) {

            if (fClip.getName() != "Encounter_with_a_Wizard.wav") {
                stopSound();
                playSound("Encounter_with_a_Wizard");
            }

            Batalha batalha = batalhas.get(z);

            if (jogadoresDerrotados.size() > 0) {
                batalha.setIniciativa(batalha.getIniciativa().stream().filter(p -> p instanceof Monstro || (p instanceof Jogador && !jogadoresDerrotados.contains(p))).toList());
                partida.setJogadores(partida.getJogadores().stream().filter(j -> !jogadoresDerrotados.contains(j)).toList());
            }

            MiniHistoria miniHistoria = batalha.getMiniHistoria();
            MiniHistoria miniHistoriaEscolhida = null;

            if (miniHistoria.getMiniHistoriaEscolhaOposta() != null) {
                MiniHistoria miniHistoriaEscolhaOposta = miniHistoria.getMiniHistoriaEscolhaOposta();
                
                escolha = 0;
                int i = 0, j = 0;
                Random rand = new Random();
                
                Jogador jogadorEscolhido = jogadores.get(rand.nextInt(jogadores.size()));

                printUtil.printStringLetraPorLetraSom(15, jogadorEscolhido.getNome() + ", ");
                printUtil.printStringLetraPorLetraSom(15, miniHistoria.getDescricao() + "\n");
                printUtil.printStringLetraPorLetraSom(15, miniHistoriaEscolhaOposta.getDescricao());
                printUtil.printStringLetraPorLetraSom(15, "\nEscolha uma opção: ");

                escolha = scannerUtil.getInt(scanner, 1, 2);

                System.out.println("");

                if (escolha == 1)
                    miniHistoriaEscolhida = miniHistoria;
                else
                    miniHistoriaEscolhida = miniHistoriaEscolhaOposta;

                printUtil.printStringLetraPorLetraSom(15, miniHistoriaEscolhida.getResultadoEscolha() + "\n\n");

                switch (miniHistoriaEscolhida.getTipoResultado()) {
                    case HABILIDADE:
                        Habilidade habilidade = habilidadeRepository.findOneRandom();

                        printUtil.printStringLetraPorLetra(5, habilidade.toString() + '\n');
                        printUtil.printStringLetraPorLetra(5, "\nPré requisitos:\n");
                        i = 0;
                        for (int preRequisito: habilidade.getPreRequisitos()) {
                            printUtil.printStringLetraPorLetra(5, TipoAtributo.values()[i] +": " + preRequisito + "\n");
                            i++;
                        }

                        List<Jogador> jogadoresAptosHabilidade = new ArrayList<>();

                        for (Jogador jogador: jogadores) {
                            if (jogador.temPreRequisitosHabilidade(habilidade) && !jogador.getHabilidades().stream().filter(h -> h.getId() == habilidade.getId()).findFirst().isPresent())
                                jogadoresAptosHabilidade.add(jogador);
                        }

                        if (jogadoresAptosHabilidade.size() > 0) {
                            printUtil.printStringLetraPorLetra(5, "\nJogadores Aptos:\n");
                            i = 1;
                            for (Jogador jogadoreAptoHabilidade: jogadoresAptosHabilidade) {
                                printUtil.printStringLetraPorLetra(5, "[" + i + "] " + jogadoreAptoHabilidade.getNome() + "\n");
                                i++;
                            }

                            printUtil.printStringLetraPorLetra(5, "[" + i + "] Jogar habilidade fora\n");

                            printUtil.printStringLetraPorLetra(5, jogadorEscolhido.getNome()+", escolha um jogador para dar essa habilidade:\n");
                            escolha = scannerUtil.getInt(scanner, 1, jogadoresAptosHabilidade.size() + 1);

                            if (escolha != i) {
                                jogadoresAptosHabilidade.get(escolha-1).getHabilidades().add(habilidade);
                                printUtil.printStringLetraPorLetra(5, "O jogador " + jogadoresAptosHabilidade.get(escolha-1).getNome() + " ganhou a habilidade!\n\n");
                            } else {
                                printUtil.printStringLetraPorLetra(5, "A habilidade foi perdida\n\n");
                            }
                        } else {
                            printUtil.printStringLetraPorLetra(5, "Ninguém da sua equipe está apto a aprender esta habilidade.\n\n");
                        }
                        break;

                    case ITEM_MANA:
                        Item itemMana = itemService.criarItemMana(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra(5, "\nVocê ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(5, itemMana.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra(5, "[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra(5, "[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(5, jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size() + 1);

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemMana);
                            printUtil.printStringLetraPorLetra(5, "O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção de mana!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra(5, "A poção foi perdida\n\n");
                        }
                        break;
                    case ITEM_VIDA:
                        Item itemVida = itemService.criarItemVida(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra(5, "\nVocê ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(5, itemVida.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra(5, "[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra(5, "[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(5, jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size() + 1);

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemVida);
                            printUtil.printStringLetraPorLetra(5, "O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção de vida!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra(5, "A poção foi perdida\n\n");
                        }
                        break;
                    
                    case ITEM_HIBRIDA:
                        Item itemHibrida = itemService.criarItemHibrida(miniHistoriaEscolhida.getDanoCura(), miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra(5, "\nVocê ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(5, itemHibrida.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra(5, "[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra(5, "[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(5, jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size() + 1);

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemHibrida);
                            printUtil.printStringLetraPorLetra(5, "O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção híbrida!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra(5, "A poção foi perdida\n\n");
                        }
                        break;

                    case ARMA:
                        RaridadeArma raridadeArma = RaridadeArma.valueOf(RaridadeArma.values()[miniHistoriaEscolhida.getDificuldade().ordinal()].name());
                        Arma arma = armaRepository.findOneByRaridade(raridadeArma);

                        printUtil.printStringLetraPorLetra(5, "Você ganhou a seguinte arma: \n");
                        printUtil.printStringLetraPorLetra(5, arma.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra(5, "[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra(5, "[" + i + "] Jogar arma fora\n");

                        printUtil.printStringLetraPorLetra(5, jogadorEscolhido.getNome() + ", escolha um jogador para dar essa arma:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size());

                        System.out.println("");

                        if (escolha != i) {
                            jogadores.get(escolha-1).setArma(arma);
                            printUtil.printStringLetraPorLetra(5, "O jogador " + jogadores.get(escolha-1).getNome() + " equipou a arma!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra(5, "A arma foi perdida\n\n");
                        }
                        break;

                    case DANO:
                        jogadorEscolhido.setVida(jogadorEscolhido.getVida() - miniHistoriaEscolhida.getDanoCura());
                        if (!jogadorEscolhido.estaVivo()) {
                            partida.removerJogador(jogadorEscolhido, batalha.getId());
                            batalha.removerJogador(jogadorEscolhido);
                            printUtil.printStringLetraPorLetraSom(200, jogadorEscolhido.getNome() + ", a sua jornada chegou ao fim...\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra(5, "A sua vida agora é " + jogadorEscolhido.getVida() + ".\n\n");
                        }
                        break;
                        
                    case CURA:
                        jogadorEscolhido.curar(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra(5, "A sua vida agora é " + jogadorEscolhido.getVida() + ".\n\n");
                        break;

                    default:
                        break;
                }
            } else { 
                printUtil.printStringLetraPorLetraSom(15, miniHistoria.getDescricao());
            }

            printUtil.printStringLetraPorLetraSom(15, "\nAperte enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();

            printUtil.clearTerminal();

            printUtil.printStringLetraPorLetraSom(15, miniHistoriaEscolhida.getTextoEntreEventos() + "\n");

            printUtil.textDelay(2000);

            if (miniHistoria.getDificuldade() == DificuldadeMonstro.CHEFE) {
                stopSound();
                playSound("The_Final_Battle");
            } else {
                stopSound();
                playSound("Entering_the_Castle");
            }

            printUtil.printStringLetraPorLetraSom(15, "\nApareceram os seguintes inimigos:\n");
            for (Monstro monstro: batalha.getMonstros()) {
                printUtil.printStringLetraPorLetraSom(15, "\n");
                printUtil.printStringLetraPorLetraSom(15, "Nome: " + monstro.getNome() + "\n");
                printUtil.printStringLetraPorLetraSom(15, "Descrição: " + monstro.getDescricao() + "\n");
                printUtil.printStringLetraPorLetraSom(15, "Classe: " + monstro.getClasse() + "\n\n");
            }

            printarMonstros(batalha.getMonstros(), true);

            printUtil.printStringLetraPorLetraSom(15, "\nAperte enter para continuar...");
            scanner.nextLine();

            Boolean inimigosDerrotados = false;

            List<Monstro> monstrosDerrotados = new ArrayList<>();

            Boolean primeiraIniciativa = true;

            while ((!batalha.getMonstros().stream().allMatch(m -> !m.estaVivo()) || !partida.getJogadores().stream().allMatch(j -> !j.estaVivo())) && !inimigosDerrotados) {

                List<Personagem> iniciativa = batalha.getIniciativa();
                for (int x = 0; x < iniciativa.size(); x++) {

                    Personagem personagem = iniciativa.get(x);

                    printUtil.clearTerminal();
                    
                    if (batalha.getMonstros().stream().allMatch(m -> !m.estaVivo())) {
                        printUtil.printStringLetraPorLetraSom(15, "Todos os inimigos foram derrotados!\n\n");
                        inimigosDerrotados = true;
                        break;
                    }
                    if (partida.getJogadores().stream().allMatch(j -> !j.estaVivo())) {
                        //stopSound();
                        //playSound("End_Game_Lose");
                        printUtil.printStringLetraPorLetraSom(200, "Todos os jogadores foram derrotados, a jornada do grupo chegou ao fim...\n\n");
                        printUtil.printStringLetraPorLetraSom(200, "Aperte enter para finalizar...");
                        scanner.nextLine();
                        return;
                    }

                    if (personagem instanceof Jogador && !jogadoresDerrotados.contains(personagem)) {
                        Jogador jogador = (Jogador) personagem;
                        int i = 0;
                        int k = 0;
                        int escolhaMonstro = 0;

                        printarMonstros(batalha.getMonstros(), false);

                        printUtil.printStringLetraPorLetra(5, "\n\nTurno: " + jogador.getNome());
                        printUtil.printStringLetraPorLetra(5, String.format("\nCON: %d FOR: %d DES: %d SAB: %d DEF: %d\n", jogador.getConstituicao(), jogador.getForca(), jogador.getDestreza(), jogador.getSabedoria(), jogador.getDefesa()));
                        printUtil.printStringLetraPorLetra(5, "\nVida: " + jogador.getVida() + " / " + jogador.getVidaMaxima());
                        printUtil.printStringLetraPorLetra(5, "\nMana: " + jogador.getMana() + " / " + jogador.getManaMaxima());
                        printUtil.printStringLetraPorLetra(5, "\nArma: " + jogador.getArma().getNome() + "  /  " + jogador.getArma().getQtdDados() + "D"+ jogador.getArma().getTipoDado() + " + " +
                            jogador.getArma().getAdicional() + "  /  " + jogador.getArma().getTipoAtributo().getValor() + "  /  " + jogador.getArma().getRaridade());
                        printUtil.printStringLetraPorLetra(5, "\n\nHabilidades\n");
                        i = 1;
                        for (Habilidade hab : jogador.getHabilidades()) {
                            printUtil.printStringLetraPorLetra(5, "[" + i + "] " + hab.toString(jogador) + "\n");
                            i++;
                        }
                        printUtil.printStringLetraPorLetra(5, "\nItens: ");
                        for (Item item : jogador.getInventario().getItens()) {
                            printUtil.printStringLetraPorLetra(5, item.toString() + "\n");
                        }
                        
                        printUtil.printStringLetraPorLetra(5, "\nAções\n");
                        printUtil.printStringLetraPorLetra(5, "[1] Atacar\n");
                        printUtil.printStringLetraPorLetra(5, "[2] Usar uma habilidade\n");
                        printUtil.printStringLetraPorLetra(5, "[3] Usar Item\n");
                        printUtil.printStringLetraPorLetra(5, "Escolha uma ação: ");
                        escolha = scannerUtil.getInt(scanner, 1, 3);

                        List<Habilidade> habilidadesNaoLancaveis = new ArrayList<>();
                        do {
                            habilidadesNaoLancaveis = jogador.getHabilidades().stream().filter(h -> jogador.getMana() < h.getCusto()).toList();

                            if (escolha == 2) {

                                if (habilidadesNaoLancaveis.size() == jogador.getHabilidades().size()) {
                                    while (escolha == 2) {
                                        printUtil.printStringLetraPorLetra(5, "Você não tem mana o suficiente para lançar alguma habilidade. Escolha novamente: ");
                                        escolha = scannerUtil.getInt(scanner, 1, 3);
                                    }
                                }
                            }
                            
                            if (escolha == 3) {
                                if (jogador.getInventario().getItens().size() <= 0) {
                                    while (escolha == 3) {
                                        printUtil.printStringLetraPorLetra(5, "Você não tem nenhum item no inventário. Escolha novamente: ");
                                        escolha = scannerUtil.getInt(scanner, 1, 3);
                                    }
                                }
                            }
                        } while((escolha == 2 && habilidadesNaoLancaveis.size() == jogador.getHabilidades().size()) || (escolha == 3 && jogador.getInventario().getItens().size() <= 0) );

                        switch (escolha) {
                            case 1:
                                printUtil.printStringLetraPorLetra(5, "\nEscolha o inimigo a ser atacado\n");

                                i = 1;

                                for (Monstro monstro: batalha.getMonstros()) {
                                    printUtil.printStringLetraPorLetra(5, "[" + i + "] - " + monstro.getNome() + "\n\n");
                                    i++;
                                }

                                escolhaMonstro = scannerUtil.getInt(scanner, 1, batalha.getMonstros().size());

                                Monstro monstroEscolhido = batalha.getMonstros().get(escolhaMonstro - 1);
                                
                                printUtil.printStringLetraPorLetra(5, jogador.aplicarDanoArma(monstroEscolhido) + "\n"); 

                                if (!monstroEscolhido.estaVivo()) {
                                    monstrosDerrotados.add(monstroEscolhido);
                                    batalha.getMonstros().remove(monstroEscolhido);
                                    batalha.getIniciativa().remove(monstroEscolhido);
                                }
                                break;
                            case 2:
                                List<Habilidade> habilidades = jogador.getHabilidades();

                                i = 1;
                                
                                for (Habilidade habilidade: habilidades) {
                                    printUtil.printStringLetraPorLetra(5, "[" + i + "] " + habilidade.toString() + "\n");
                                    i++;
                                }

                                printUtil.printStringLetraPorLetra(5, "Escolha uma habilidade: ");

                                int escolhaHabilidade = scannerUtil.getInt(scanner, 1, habilidades.size());

                                Habilidade habilidadeEscolhida = jogador.getHabilidades().get(escolhaHabilidade - 1);

                                int areaHabilidade = habilidadeEscolhida.getArea();

                                switch (habilidadeEscolhida.getTipo()) {
                                    case CURA:
                                        List<Jogador> jogadoresEscolhidos = new ArrayList<>();
                                        List<Jogador> jogadoresAux = new ArrayList<>(jogadores);
                                    
                                        printUtil.printStringLetraPorLetra(5, "Jogadore(s)\n");
                                        
                                        k = 0;
                                        for (i = 0; i < (areaHabilidade > jogadores.size() ? jogadores.size() : areaHabilidade); i++) {
                                            for (Jogador j : jogadoresAux) {
                                                k++;
                                                printUtil.printStringLetraPorLetra(5, "[" + k + "]" + j.getNome() + "\n");
                                            }
                                            printUtil.printStringLetraPorLetra(5, "\nEscolha um jogador: ");
                                            int escolhaJogador = scannerUtil.getInt(scanner, 1, jogadoresAux.size());
                                            jogadoresEscolhidos.add(jogadoresAux.get(escolhaJogador - 1));
                                            jogadoresAux.remove(escolhaJogador - 1);
                                        }
                                        
                                        for (Jogador jogadorEscolhido: jogadoresEscolhidos) {
                                            printUtil.printStringLetraPorLetra(5, jogador.aplicarCuraHabilidade(jogadorEscolhido, habilidadeEscolhida) + "\n");
                                        }
                                        break;
                                    default:
                                        List<Monstro> monstrosEscolhidos = new ArrayList<>();
                                        List<Monstro> monstrosAux = new ArrayList<>(batalha.getMonstros());

                                        printUtil.printStringLetraPorLetra(5, "\nInimigo(s)\n");
                                        
                                        k = 1;
                                        for (i = 0; i < (areaHabilidade > batalha.getMonstros().size() ? batalha.getMonstros().size() : areaHabilidade); i++) {
                                            for (Monstro m: monstrosAux) {
                                                printUtil.printStringLetraPorLetra(5, "[" + k + "]" + m.getNome() + "\n");
                                                k++;
                                            }
                                            printUtil.printStringLetraPorLetra(5, "\nEscolha um inimigo: ");
                                            int escolhaJogador = scannerUtil.getInt(scanner, 1, monstrosAux.size());
                                            monstrosEscolhidos.add(monstrosAux.get(escolhaJogador - 1));
                                            monstrosAux.remove(escolhaJogador - 1);
                                            k = 1;
                                        }
                                
                                        for (Monstro m: monstrosEscolhidos) {
                                            printUtil.printStringLetraPorLetra(5, jogador.aplicarDanoHabilidade(m, habilidadeEscolhida) + "\n");

                                            if (!m.estaVivo()) {
                                                monstrosDerrotados.add(m);
                                                batalha.getMonstros().remove(m);
                                                batalha.getIniciativa().remove(m);
                                            }
                                        }
                                    break;
                                }
                                break;
                            case 3:
                                int escolhaItem = 0;
                                int escolhaJogador = 0;
                                List<Item> itens = jogador.getInventario().getItens();
                                
                                printUtil.printStringLetraPorLetra(5, "Escolha um item\n");

                                i = 1;
                                for (Item item: itens) {
                                    printUtil.printStringLetraPorLetra(5, "[" + i + "]" + item.getNome() + "\n");
                                    i++;
                                }

                                escolhaItem = scannerUtil.getInt(scanner, 1, itens.size());

                                i = 1;
                                for (Jogador j: jogadores) {
                                    printUtil.printStringLetraPorLetra(5, "[" + i + "] " + j.getNome() + "\n");
                                    i++;
                                }
        
                                printUtil.printStringLetraPorLetra(5, "Escolha um jogador para usar seu item:\n");
                                escolhaJogador = scannerUtil.getInt(scanner, 1, jogadores.size());

                                jogador.usarItem(escolhaItem - 1, jogadores.get(escolhaJogador - 1));
                                break;
                            default:
                                break;
                        }

                        printUtil.printStringLetraPorLetra(5, "Aperte enter para continuar...");
                        scanner.nextLine();
                        scanner.nextLine();
                    } else if (!monstrosDerrotados.contains(personagem)) {        // Personagem é Monstro
                        Monstro monstro = (Monstro) personagem;

                        printUtil.clearTerminal();
                        printUtil.printStringLetraPorLetra(5, "Turno de inimigo: " + monstro.getNome() + "...\n" );
                        printUtil.textDelay(2000);

                        Random random = new Random();
                        int chance = random.nextInt(100);
                         
                        if (chance < 70 || monstro.getHabilidades().stream().allMatch(h -> h.getTipo() != TipoAtributo.CURA)) {      // Habilidade de DANO
                            List<Habilidade> habilidadesDano = monstro.getHabilidades().stream().filter(h -> h.getTipo() != TipoAtributo.CURA).toList();
                            int qtdHabilidades = habilidadesDano.size();
                            chance = random.nextInt(qtdHabilidades);
                            
                            Habilidade habilidade = habilidadesDano.get(chance);

                            List<Jogador> jogadoresEscolhidos = new ArrayList<>();
                            Jogador jogadorEscolhido = null;

                            for (int c = 1; c <= habilidade.getArea() && jogadoresEscolhidos.size() < jogadores.size(); c++) {

                                do {
                                    chance = random.nextInt(jogadores.size());

                                    jogadorEscolhido = jogadores.get(chance);
                                } while (jogadoresEscolhidos.contains(jogadorEscolhido));

                                jogadoresEscolhidos.add(jogadorEscolhido);

                                printUtil.printStringLetraPorLetra(5, monstro.aplicarDanoHabilidade(jogadorEscolhido, habilidade) + "\n");

                                if (!jogadorEscolhido.estaVivo()) {
                                    jogadoresDerrotados.add(jogadorEscolhido);
                                    batalha.getIniciativa().remove(jogadorEscolhido);
                                }
                            }

                        } else {        // Habilidade de CURA
                            List<Habilidade> habilidadesCura = monstro.getHabilidades().stream().filter(h -> h.getTipo() == TipoAtributo.CURA).toList();
                            int qtdHabilidades = habilidadesCura.size();
                            chance = random.nextInt(qtdHabilidades);
                            
                            Habilidade habilidade = habilidadesCura.get(chance);
                            
                            List<Monstro> monstrosEscolhidos = new ArrayList<>();
                            Monstro monstroEscolhido = null;

                            for (int c = 1; c <= habilidade.getArea() && monstrosEscolhidos.size() < batalha.getMonstros().size(); c++) {

                                do {
                                    chance = random.nextInt(batalha.getMonstros().size());

                                    monstroEscolhido = batalha.getMonstros().get(chance);
                                } while (monstrosEscolhidos.contains(monstroEscolhido));

                                monstrosEscolhidos.add(monstroEscolhido);

                                printUtil.printStringLetraPorLetra(5, monstro.aplicarCuraHabilidade(monstroEscolhido, habilidade) + "\n");
                            }
                        }

                        printUtil.printStringLetraPorLetra(5, "Aperte enter para continuar...");
                        scanner.nextLine();
                    }

                }
            }

            if (!modoDificil) {
                for (Jogador jogador : partida.getJogadores()) {
                    jogador.regenerarManaPosBatalha();
                    jogador.regenerarVidaPosBatalha();
                }
            }
        }

        stopSound();
        playSound("End_Game_Win");
        printUtil.printStringLetraPorLetraSom(200, "Aperte enter para finalizar...");
        scanner.nextLine();
    }

    private static void printarJogadores(List<Jogador> jogadores) {
        printUtil.printStringLetraPorLetraSom(15, "Grupo da aventura:\n\n");

        for (Jogador jogador: jogadores) {
            printUtil.printStringLetraPorLetraSom(15, jogador.toString() + "\n\n");
        }
    }

    private static void printarMonstros(List<Monstro> monstros, Boolean letraPorLetra) {
        Collections.sort(monstros, new Comparator<Monstro>() {
            public int compare(Monstro m1, Monstro m2) {
                Integer m1LinesCount = m1.getAscii().split("\n").length;
                Integer m2LinesCount = m2.getAscii().split("\n").length;

                return m1LinesCount.compareTo(m2LinesCount);
            }
            
        });

        int maiorAsciiMonstro = monstros.get(monstros.size() - 1).getAscii().split("\n").length;

        Collections.shuffle(monstros);

        int i = 0;

        for (i = 0; i < maiorAsciiMonstro; i++) {
            for (Monstro monstro: monstros) {
                List<String> qtdAscii = Arrays.asList(monstro.getAscii().split("\n"));
                if (letraPorLetra)
                    printUtil.printStringLetraPorLetra(3, (qtdAscii.size() > i ? String.format("%-50s", qtdAscii.get(i)) : String.format("%-50s", "")));
                else
                    System.out.print((qtdAscii.size() > i ? String.format("%-50s", qtdAscii.get(i)) : String.format("%-50s", "")));
            }
            System.out.println("");
        }
    }

    public static synchronized void playSound(String sound) {

        fClip = new File("musicas/" + sound + ".wav");
        
        try {
            audioIn = AudioSystem.getAudioInputStream(fClip);  
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public static synchronized void stopSound() {
        try {
            clip.stop();
            audioIn.close();
            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
