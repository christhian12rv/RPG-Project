import services.ArmaService;
import services.HabilidadeService;
import services.InventarioService;
import services.ItemService;
import utils.*;
import utils.EntitiesUtils.*;

import javax.persistence.EntityManager;

import entities.Arma;
import entities.Batalha;
import entities.Habilidade;
import entities.Historia;
import entities.Item;
import entities.Jogador;
import entities.MiniHistoria;
import entities.Partida;
import enums.RaridadeArma;
import enums.TipoAtributo;
import enums.TipoResultadoMiniHistoria;
import repositories.*;

import java.util.ArrayList;
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
        
        jogadores = jogadorUtil.criarJogadores();
        printUtil.printStringLetraPorLetraSom("Jogadores criados\n\n");


        historia = historiaUtil.historiaRandomica();

        partida = partidaUtil.criarPartida(jogadores, historia);
        printUtil.printStringLetraPorLetraSom("Partida criada! O jogo irá começar...\n\n");

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
                int i = 0;
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
                            if (jogador.temPreRequisitosHabilidade(habilidade) &&
                                (jogador.getId() == jogadorEscolhido.getId() && !jogadorEscolhido.getHabilidades().stream().filter(h -> h.getId() == habilidade.getId()).findFirst().isPresent()))
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
                        }                    
                        break;
                        
                    case CURA:
                        if (jogadorEscolhido.getVida() + miniHistoriaEscolhida.getDanoCura() > jogadorEscolhido.getVidaMaxima()) {
                            jogadorEscolhido.setVida(jogadorEscolhido.getVidaMaxima());
                        } else {
                            jogadorEscolhido.setVida(jogadorEscolhido.getVida() + miniHistoriaEscolhida.getDanoCura());
                        }
                        break;

                    default:
                        break;
                }
            } else { 
                printUtil.printStringLetraPorLetraSom(miniHistoria.getDescricao());
            }
        }

    }

    private static void printarJogadores(List<Jogador> jogadores) {
        printUtil.printStringLetraPorLetraSom("Grupo da aventura:\n\n");

        for (Jogador jogador: jogadores) {
            printUtil.printStringLetraPorLetraSom(jogador.toString() + "\n\n");
        }
    }
}
