import services.ArmaService;
import services.HabilidadeService;
import services.InventarioService;
import services.ItemService;
import utils.*;
import utils.EntitiesUtils.*;

import javax.persistence.EntityManager;

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
import enums.RaridadeArma;
import enums.TipoAtributo;
import repositories.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public static void main(String[] args) {
        // Logger.getRootLogger().setLevel(Level.OFF);
        // Logger.getLogger("org.hibernate").setLevel(Level.OFF);

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
        printUtil.printStringLetraPorLetraSom("Jogadores criados\n\n");

        historia = historiaUtil.historiaRandomica();

        partida = partidaUtil.criarPartida(jogadores, historia);
        printUtil.printStringLetraPorLetraSom("Partida criada! O jogo irá começar...\n\n");

        printUtil.textDelay(2000);
        printUtil.clearTerminal();

        começarPartida(partida);

        entityManager.close();
        JPAUtil.shutdown();
    }

    private static void começarPartida(Partida partida) {

        Boolean opcaoInvalida = false;

        Historia historia = partida.getHistoria();
        List<Batalha> batalhas = partida.getBatalhas();
        List<Jogador> jogadores = partida.getJogadores();

        printUtil.printStringLetraPorLetraSom(historia.getDescricao() + "\n\n");
        printUtil.textDelay(1000);

        printarJogadores(jogadores);

        for (Batalha batalha: batalhas) {
            
            MiniHistoria miniHistoria = batalha.getMiniHistoria();

            if (miniHistoria.getMiniHistoriaEscolhaOposta() != null) {
                MiniHistoria miniHistoriaEscolhaOposta = miniHistoria.getMiniHistoriaEscolhaOposta();
                int escolha = 0;
                int i = 0, j = 0;
                Random rand = new Random();
                
                Jogador jogadorEscolhido = jogadores.get(rand.nextInt(jogadores.size()));

                printUtil.printStringLetraPorLetraSom(jogadorEscolhido.getNome() + ", ");
                printUtil.printStringLetraPorLetraSom(miniHistoria.getDescricao() + "\n");
                printUtil.printStringLetraPorLetraSom(miniHistoriaEscolhaOposta.getDescricao());
                printUtil.printStringLetraPorLetraSom("\nEscolha uma opção: ");

                escolha = scannerUtil.getInt(scanner, 1, 2);

                System.out.println("");

                MiniHistoria miniHistoriaEscolhida = null;

                if (escolha == 1)
                    miniHistoriaEscolhida = miniHistoria;
                else
                    miniHistoriaEscolhida = miniHistoriaEscolhaOposta;

                printUtil.printStringLetraPorLetraSom(miniHistoriaEscolhida.getResultadoEscolha() + "\n");

                switch (miniHistoriaEscolhida.getTipoResultado()) {
                    case HABILIDADE:
                        Habilidade habilidade = habilidadeRepository.findOneRandom();

                        printUtil.printStringLetraPorLetra(habilidade.toString() + '\n');
                        printUtil.printStringLetraPorLetra("\nPré requisitos:\n");
                        i = 0;
                        for (int preRequisito: habilidade.getPreRequisitos()) {
                            printUtil.printStringLetraPorLetra(TipoAtributo.values()[i] +": " + preRequisito + "\n");
                            i++;
                        }

                        List<Jogador> jogadoresAptosHabilidade = new ArrayList<>();

                        for (Jogador jogador: jogadores) {
                            if (jogador.temPreRequisitosHabilidade(habilidade) && !jogador.getHabilidades().stream().filter(h -> h.getId() == habilidade.getId()).findFirst().isPresent())
                                jogadoresAptosHabilidade.add(jogador);
                        }

                        if (jogadoresAptosHabilidade.size() > 0) {
                            printUtil.printStringLetraPorLetra("\nJogadores Aptos:\n");
                            i = 1;
                            for (Jogador jogadoreAptoHabilidade: jogadoresAptosHabilidade) {
                                printUtil.printStringLetraPorLetra("[" + i + "] " + jogadoreAptoHabilidade.getNome() + "\n");
                                i++;
                            }

                            printUtil.printStringLetraPorLetra("[" + i + "] Jogar habilidade fora\n");

                            printUtil.printStringLetraPorLetra(jogadorEscolhido.getNome()+", escolha um jogador para dar essa habilidade:\n");
                            escolha = scannerUtil.getInt(scanner, 1, jogadoresAptosHabilidade.size() + 1);

                            if (escolha != i) {
                                jogadoresAptosHabilidade.get(escolha-1).getHabilidades().add(habilidade);
                                printUtil.printStringLetraPorLetra("O jogador " + jogadoresAptosHabilidade.get(escolha-1).getNome() + " ganhou a habilidade!\n\n");
                            } else {
                                printUtil.printStringLetraPorLetra("A habilidade foi perdida\n\n");
                            }
                        } else {
                            printUtil.printStringLetraPorLetra("Ninguém da sua equipe está apto a aprender esta habilidade.\n\n");
                        }
                        break;

                    case ITEM_MANA:
                        Item itemMana = itemService.criarItemMana(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra("Você ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(itemMana.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra("[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra("[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size());

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemMana);
                            printUtil.printStringLetraPorLetra("O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção de mana!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra("A poção foi perdida\n\n");
                        }
                        break;
                    case ITEM_VIDA:
                        Item itemVida = itemService.criarItemVida(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra("Você ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(itemVida.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra("[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra("[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size());

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemVida);
                            printUtil.printStringLetraPorLetra("O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção de vida!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra("A poção foi perdida\n\n");
                        }
                        break;
                    
                    case ITEM_HIBRIDA:
                        Item itemHibrida = itemService.criarItemHibrida(miniHistoriaEscolhida.getDanoCura(), miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra("Você ganhou o seguinte item: \n");
                        printUtil.printStringLetraPorLetra(itemHibrida.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra("[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra("[" + i + "] Jogar item fora\n");

                        printUtil.printStringLetraPorLetra(jogadorEscolhido.getNome() + ", escolha um jogador para dar essa poção:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size());

                        if (escolha != i) {
                            jogadores.get(escolha-1).getInventario().getItens().add(itemHibrida);
                            printUtil.printStringLetraPorLetra("O jogador " + jogadores.get(escolha-1).getNome() + " ganhou a poção híbrida!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra("A poção foi perdida\n\n");
                        }
                        break;

                    case ARMA:
                        RaridadeArma raridadeArma = RaridadeArma.valueOf(RaridadeArma.values()[miniHistoriaEscolhida.getDificuldade().ordinal()].name());
                        Arma arma = armaRepository.findOneByRaridade(raridadeArma);

                        printUtil.printStringLetraPorLetra("Você ganhou a seguinte arma: \n");
                        printUtil.printStringLetraPorLetra(arma.toString() + '\n');

                        i = 1;
                        for (Jogador jogador: jogadores) {
                            printUtil.printStringLetraPorLetra("[" + i + "] " + jogador.getNome() + "\n");
                            i++;
                        }

                        printUtil.printStringLetraPorLetra("[" + i + "] Jogar arma fora\n");

                        printUtil.printStringLetraPorLetra(jogadorEscolhido.getNome() + ", escolha um jogador para dar essa arma:\n");
                        escolha = scannerUtil.getInt(scanner, 1, jogadores.size());

                        System.out.println("");

                        if (escolha != i) {
                            jogadores.get(escolha-1).setArma(arma);
                            printUtil.printStringLetraPorLetra("O jogador " + jogadores.get(escolha-1).getNome() + " equipou a arma!\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra("A arma foi perdida\n\n");
                        }
                        break;

                    case DANO:
                        jogadorEscolhido.setVida(jogadorEscolhido.getVida() - miniHistoriaEscolhida.getDanoCura());
                        if (!jogadorEscolhido.estaVivo()) {
                            partida.removerJogador(jogadorEscolhido, batalha.getId());
                            batalha.removerJogador(jogadorEscolhido);
                            printUtil.printStringLetraPorLetraLentoSom(jogadorEscolhido.getNome() + ", a sua jornada chegou ao fim...\n\n");
                        } else {
                            printUtil.printStringLetraPorLetra("A sua vida agora é " + jogadorEscolhido.getVida() + ".\n\n");
                        }
                        break;
                        
                    case CURA:
                        jogadorEscolhido.curar(miniHistoriaEscolhida.getDanoCura());
                        printUtil.printStringLetraPorLetra("A sua vida agora é " + jogadorEscolhido.getVida() + ".\n\n");
                        break;

                    default:
                        break;
                }
            } else { 
                printUtil.printStringLetraPorLetraSom(miniHistoria.getDescricao());
            }

            printUtil.printStringLetraPorLetraSom("Aperte enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();

            Boolean inimigosDerrotados = false;

            while ((!batalha.getMonstros().stream().allMatch(m -> !m.estaVivo()) || !partida.getJogadores().stream().allMatch(j -> !j.estaVivo())) && !inimigosDerrotados) {
                List<Monstro> monstrosDerrotados = new ArrayList<>();
                for (Monstro m: monstrosDerrotados) {
                    batalha.getMonstros().remove(m);
                    batalha.getIniciativa().remove(m);
                }
                
                for (Personagem personagem: batalha.getIniciativa()) {
                    printUtil.clearTerminal();
                    
                    if (batalha.getMonstros().stream().allMatch(m -> !m.estaVivo())) {
                        printUtil.printStringLetraPorLetraSom("Todos os inimigos foram derrotados!\n\n");
                        inimigosDerrotados = true;
                        break;
                    }
                    if (partida.getJogadores().stream().allMatch(j -> !j.estaVivo())) {
                        printUtil.printStringLetraPorLetraLentoSom("Todos os jogadores foram derrotados, a jornada do grupo chegou fim...\n\n");
                        return;
                    }

                    if (personagem instanceof Jogador) { // printador
                        Jogador jogador = (Jogador) personagem;
                        int i = 0;
                        int k = 0;
                        int escolhaMonstro = 0;

                        printarMonstros(batalha.getMonstros());

                        printUtil.printStringLetraPorLetra("Turno: " + jogador.getNome());
                        printUtil.printStringLetraPorLetra(String.format("\nCON: %d FOR: %d DES: %d SAB: %d DEF: %d\n", jogador.getConstituicao(), jogador.getForca(), jogador.getDestreza(), jogador.getSabedoria(), jogador.getDefesa()));
                        printUtil.printStringLetraPorLetra("\nVida: " + jogador.getVida() + " / " + jogador.getVidaMaxima());
                        printUtil.printStringLetraPorLetra("\nMana: " + jogador.getMana() + " / " + jogador.getManaMaxima());
                        printUtil.printStringLetraPorLetra("\nArma: " + jogador.getArma().getNome() + "  /  " + jogador.getArma().getQtdDados() + "D"+ jogador.getArma().getTipoDado() + " + " +
                            jogador.getArma().getAdicional() + "  /  " + jogador.getArma().getTipoAtributo().getValor() + "  /  " + jogador.getArma().getRaridade());
                        printUtil.printStringLetraPorLetra("\n\nHabilidades\n");
                        i = 1;
                        for (Habilidade hab : jogador.getHabilidades()) {
                            printUtil.printStringLetraPorLetra("[" + i + "] " + hab.toString() + "\n");
                            i++;
                        }
                        printUtil.printStringLetraPorLetra("\nItens: ");
                        for (Item item : jogador.getInventario().getItens()) {
                            printUtil.printStringLetraPorLetra(item.toString() + "\n");
                        }
                        
                        printUtil.printStringLetraPorLetra("\nAções\n");
                        printUtil.printStringLetraPorLetra("[1] Atacar\n");
                        printUtil.printStringLetraPorLetra("[2] Usar uma habilidade\n");
                        printUtil.printStringLetraPorLetra("[3] Usar Item\n");
                        printUtil.printStringLetraPorLetra("Escolha uma ação: ");
                        int escolha = scannerUtil.getInt(scanner, 1, 3);

                        List<Habilidade> habilidadesNaoLancaveis = null;
                        do {
                            if (escolha == 2) {
                                habilidadesNaoLancaveis = jogador.getHabilidades().stream().filter(h -> jogador.getMana() < h.getCusto()).toList();

                                if (habilidadesNaoLancaveis.size() == jogador.getHabilidades().size()) {
                                    while (escolha < 1 || escolha > 3) {
                                        printUtil.printStringLetraPorLetra("Você não tem mana o suficiente para lançar alguma habilidade. Escolha novamente: ");
                                        escolha = scannerUtil.getInt(scanner, 1, 3);
                                    }
                                }
                            }
                            
                            if (escolha == 3) {
                                if (jogador.getInventario().getItens().size() <= 0) {
                                    while (escolha < 1 || escolha > 3) {
                                        printUtil.printStringLetraPorLetra("Você não tem nenhum item no inventário. Escolha novamente: ");
                                        escolha = scannerUtil.getInt(scanner, 1, 3);
                                    }
                                }
                            }
                        } while((escolha == 2 && habilidadesNaoLancaveis.size() == jogador.getHabilidades().size()) || (escolha == 3 && jogador.getInventario().getItens().size() <= 0) );

                        switch (escolha) {
                            case 1:
                                printUtil.printStringLetraPorLetra("\nEscolha o inimigo a ser atacado\n");

                                i = 1;

                                for (Monstro monstro: batalha.getMonstros()) {
                                    printUtil.printStringLetraPorLetra("[" + i + "] - " + monstro.getNome() + "\n\n");
                                    i++;
                                }

                                escolhaMonstro = scannerUtil.getInt(scanner, 1, batalha.getMonstros().size());

                                Monstro monstroEscolhido = batalha.getMonstros().get(escolhaMonstro - 1);
                                
                                printUtil.printStringLetraPorLetra(jogador.aplicarDanoArma(monstroEscolhido) + "\n"); 

                                if (!monstroEscolhido.estaVivo()) {
                                    monstrosDerrotados.add(monstroEscolhido);
                                }
                                break;
                            case 2:
                                List<Habilidade> habilidades = jogador.getHabilidades();

                                i = 1;
                                
                                for (Habilidade habilidade: habilidades) {
                                    printUtil.printStringLetraPorLetra("[" + i + "] " + habilidade.toString() + "\n");
                                    i++;
                                }

                                printUtil.printStringLetraPorLetra("Escolha uma habilidade:");

                                int escolhaHabilidade = scannerUtil.getInt(scanner, 1, habilidades.size());

                                Habilidade habilidadeEscolhida = jogador.getHabilidades().get(escolhaHabilidade - 1);

                                int areaHabilidade = habilidadeEscolhida.getArea();

                                switch (habilidadeEscolhida.getTipo()) {
                                    case CURA:
                                        List<Jogador> jogadoresEscolhidos = new ArrayList<>();
                                        List<Jogador> jogadoresAux = new ArrayList<>(jogadores);
                                    
                                        printUtil.printStringLetraPorLetra("Jogadore(s)\n");
                                        
                                        k = 0;
                                        for (i = 0; i < (areaHabilidade > jogadores.size() ? jogadores.size() : areaHabilidade); i++) {
                                            for (Jogador j : jogadoresAux) {
                                                k++;
                                                printUtil.printStringLetraPorLetra("[" + k + "]" + j.getNome() + "\n");
                                            }
                                            printUtil.printStringLetraPorLetra("\nEscolha um jogador: ");
                                            int escolhaJogador = scannerUtil.getInt(scanner, 1, jogadoresAux.size());
                                            jogadoresEscolhidos.add(jogadoresAux.get(escolhaJogador - 1));
                                            jogadoresAux.remove(escolhaJogador - 1);
                                        }
                                        
                                        for (Jogador jogadorEscolhido: jogadoresEscolhidos) {
                                            printUtil.printStringLetraPorLetra(jogador.aplicarCuraHabilidade(jogadorEscolhido, habilidadeEscolhida) + "\n");
                                        }
                                        break;
                                    default:
                                        List<Monstro> monstrosEscolhidos = new ArrayList<>();
                                        List<Monstro> monstrosAux = new ArrayList<>(batalha.getMonstros());

                                        printUtil.printStringLetraPorLetra("\nInimigo(s)\n");
                                        
                                        k = 1;
                                        for (i = 0; i < (areaHabilidade > batalha.getMonstros().size() ? batalha.getMonstros().size() : areaHabilidade); i++) {
                                            for (Monstro m: monstrosAux) {
                                                printUtil.printStringLetraPorLetra("[" + k + "]" + m.getNome() + "\n");
                                                k++;
                                            }
                                            printUtil.printStringLetraPorLetra("\nEscolha um inimigo: ");
                                            int escolhaJogador = scannerUtil.getInt(scanner, 1, monstrosAux.size());
                                            monstrosEscolhidos.add(monstrosAux.get(escolhaJogador - 1));
                                            monstrosAux.remove(escolhaJogador - 1);
                                            k = 1;
                                        }
                                
                                        for (Monstro m: monstrosEscolhidos) {
                                            printUtil.printStringLetraPorLetra(jogador.aplicarDanoHabilidade(m, habilidadeEscolhida) + "\n");

                                            if (!m.estaVivo()) {
                                                monstrosDerrotados.add(m);
                                            }
                                        }
                                    break;
                                }
                                break;
                            case 3:
                                int escolhaItem = 0;
                                int escolhaJogador = 0;
                                List<Item> itens = jogador.getInventario().getItens();
                                
                                printUtil.printStringLetraPorLetra("Escolha um item\n");

                                i = 1;
                                for (Item item: itens) {
                                    printUtil.printStringLetraPorLetra("[" + i + "]" + item.getNome());
                                    i++;
                                }

                                escolhaItem = scannerUtil.getInt(scanner, 1, itens.size());

                                i = 1;
                                for (Jogador j: jogadores) {
                                    printUtil.printStringLetraPorLetra("[" + i + "] " + j.getNome() + "\n");
                                    i++;
                                }
        
                                printUtil.printStringLetraPorLetra("Escolha um jogador para usar seu item:\n");
                                escolhaJogador = scannerUtil.getInt(scanner, 1, jogadores.size());

                                jogador.usarItem(escolhaItem - 1, jogadores.get(escolhaJogador - 1));
                                break;
                            default:
                                break;
                        }

                        printUtil.printStringLetraPorLetra("Aperte enter para continuar...");
                        scanner.nextLine();
                        scanner.nextLine();
                    }

                }
            }
        }
    }

    private static void printarJogadores(List<Jogador> jogadores) {
        printUtil.printStringLetraPorLetraSom("Grupo da aventura:\n\n");

        for (Jogador jogador: jogadores) {
            printUtil.printStringLetraPorLetraSom(jogador.toString() + "\n\n");
        }
    }

    private static void printarMonstros(List<Monstro> monstros) {
        Collections.sort(monstros, new Comparator<Monstro>() {
            public int compare(Monstro m1, Monstro m2) {
                Integer m1LinesCount = m1.getAscii().split("\n").length;
                Integer m2LinesCount = m2.getAscii().split("\n").length;

                return m1LinesCount.compareTo(m2LinesCount);
            }
            
        });

        int maiorAsciiMonstro = monstros.get(monstros.size() - 1).getAscii().split("\n").length;

        Collections.shuffle(monstros);

        int i = 0, j = 0;

        /*List<List<String>> nomes = new ArrayList<List<String>>();
        List<List<String>> descricoes = new ArrayList<List<String>>();
        List<List<String>> classes = new ArrayList<List<String>>();

        List<List<String>> nomesOrdenados = new ArrayList<List<String>>();
        List<List<String>> descricoesOrdenadas = new ArrayList<List<String>>();
        List<List<String>> classesOrdenadas = new ArrayList<List<String>>();
        
        for (Monstro monstro: monstros) {
            String nome = monstro.getNome().replaceAll("(.{49})", "$1\n");
            if (nome.length() > (50 - 6))
                nome = nome.substring(0,11) + "\n" + nome.substring(11, nome.length());
            
            nomes.add(Arrays.asList(nome.split("\n")));
        }
        System.out.println("");
        for (Monstro monstro: monstros) {
            String descricao = monstro.getDescricao().replaceAll("(.{49})", "$1\n");
            if (descricao.length() > (50 - 11))
                descricao = descricao.substring(0,11) + "\n" + descricao.substring(11, descricao.length());
                
            descricoes.add(Arrays.asList(descricao.split("\n")));
        }
        System.out.println("");
        for (Monstro monstro: monstros) {
            String classe = monstro.getClasse().replaceAll("(.{49})", "$1\n");
            if (classe.length() > (50 - 8))
                classe = classe.substring(0,11) + "\n" + classe.substring(11, classe.length());

            classes.add(Arrays.asList(classe.split("\n")));
        }

        nomesOrdenados.add(nomes.get(0));
        for (i = 1; i < nomes.size(); i++) {
            for (j = 0; j <= nomesOrdenados.size(); j++) {
                if (j == nomesOrdenados.size()) {
                    nomesOrdenados.add(nomes.get(i));
                    break;
                } else if (nomes.get(i).size() > nomesOrdenados.get(j).size()) {
                    if (j + 1 >= nomesOrdenados.size()) {
                        nomesOrdenados.add(nomes.get(i));
                        break;
                    }
                    nomesOrdenados.add(j + 1, nomes.get(i));
                    break;
                }
            }
        }
        descricoesOrdenadas.add(descricoes.get(0));
        for (i = 1; i < descricoes.size(); i++) {
            for (j = 0; j <= descricoesOrdenadas.size(); j++) {
                if (j == descricoesOrdenadas.size()) {
                    descricoesOrdenadas.add(descricoes.get(i));
                    break;
                } else if (descricoes.get(i).size() > descricoesOrdenadas.get(j).size()) {
                    if (j + 1 >= descricoesOrdenadas.size()) {
                        descricoesOrdenadas.add(nomes.get(i));
                        break;
                    }
                    descricoesOrdenadas.add(j + 1, descricoes.get(i));
                    break;
                }
            }
        }
        classesOrdenadas.add(classes.get(0));
        for (i = 1; i < classes.size(); i++) {
            for (j = 0; j <= classesOrdenadas.size(); j++) {
                if (j == classesOrdenadas.size()) {
                    classesOrdenadas.add(classes.get(i));
                    break;
                } else if (classes.get(i).size() > classesOrdenadas.get(j).size()) {
                    if (j + 1 >= classesOrdenadas.size()) {
                        classesOrdenadas.add(nomes.get(i));
                        break;
                    }
                    classesOrdenadas.add(j + 1, classes.get(i));
                    break;
                }
            }
        }

        for (List<String> nome: nomesOrdenados) {
            nome.add(0, "Nome:");
        }
        for (List<String> descricao: descricoesOrdenadas) {
            descricao.add(0, "Descrição:");
        }
        for (List<String> classe: classesOrdenadas) {
            classe.add(0, "Classe:");
        }

        for (i = 0; i < nomesOrdenados.get(0).size(); i++) {
            for (List<String> nome: nomesOrdenados) {
                if (i < nome.size());
                    System.out.print(String.format("%-50s", nome.get(i)));
            }
            System.out.println("");
        }
        for (i = 0; i < descricoesOrdenadas.get(0).size(); i++) {
            for (List<String> descricao: descricoesOrdenadas) {
                if (i < descricao.size());
                    System.out.print(String.format("%-50s", descricao.get(i)));
            }
            System.out.println("");
        }
        for (i = 0; i < classesOrdenadas.get(0).size(); i++) {
            for (List<String> classe: classesOrdenadas) {
                if (i < classe.size());
                    System.out.print(String.format("%-50s", classe.get(i)));
            }
            System.out.println("");
        }

        System.out.println("");*/

        for (i = 0; i < maiorAsciiMonstro; i++) {
            for (Monstro monstro: monstros) {
                List<String> qtdAscii = Arrays.asList(monstro.getAscii().split("\n"));
                System.out.print((qtdAscii.size() > i ? String.format("%-50s", qtdAscii.get(i)) : String.format("%-50s", "")));
            }
            System.out.println("");
        }
    }
}
