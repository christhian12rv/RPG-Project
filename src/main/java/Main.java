import entitys.Historia;
import entitys.Jogador;
import entitys.Partida;
import repositorys.*;
import services.ArmaService;
import services.HabilidadeService;
import services.InventarioService;
import services.ItemService;
import utils.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        /*
        MonstroRepository monstroRepository = new MonstroRepository(entityManager);


        Monstro monstro = new Monstro();
        monstro.setDificuldade(DificuldadeMonstro.INICIANTE);

        monstroRepository.save(monstro);

        List<Monstro> monstros = monstroRepository.findAll();

        for (Monstro m: monstros) {
            System.out.println("Id = " + m.getId() + "  Dificuldade = " + m.getDificuldade());
        }

        monstro = monstroRepository.findById(4);
        if (monstro == null)
            System.out.println("Esse monstro não existe");
        else
            System.out.println("\nId = " + monstro.getId() + "  Dificuldade = " + monstro.getDificuldade());

        System.out.println(monstroRepository.deleteById(4));
        */

        //TimeUnit.NANOSECONDS.sleep( Long.MAX_VALUE - 1);

        Scanner scanner = new Scanner(System.in);
        int qtdJogadores = 0;
        int i = 0;
        List<Jogador> jogadores = null;
        Historia historia = null;

        PersonagemUtil personagemUtil = new PersonagemUtil();
        JogadorUtil jogadorUtil = new JogadorUtil();
        HistoriaUtil historiaUtil = new HistoriaUtil();
        BatalhaUtil batalhaUtil = new BatalhaUtil();
        PartidaUtil partidaUtil = new PartidaUtil();

        ArmaService armaService = new ArmaService();
        HabilidadeService habilidadeService = new HabilidadeService();
        InventarioService inventarioService = new InventarioService();
        ItemService itemService = new ItemService();

        ArmaRepository armaRepository = new ArmaRepository(entityManager);
        BatalhaRepository batalhaRepository = new BatalhaRepository(entityManager);
        JogadorRepository jogadorRepository = new JogadorRepository(entityManager);
        HistoriaRepository historiaRepository = new HistoriaRepository(entityManager);
        HabilidadeRepository habilidadeRepository = new HabilidadeRepository(entityManager);
        InventarioRepository inventarioRepository = new InventarioRepository(entityManager);
        ItemRepository itemRepository = new ItemRepository(entityManager);
        MonstroRepository monstroRepository = new MonstroRepository(entityManager);
        PartidaRepository partidaRepository = new PartidaRepository(entityManager);

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


        jogadores = jogadorUtil.criarJogadores(qtdJogadores);
        System.out.println("Jogadores criados");

        historia = historiaUtil.historiaRandomica();

        Partida partida = partidaUtil.criarPartida(jogadores, historia);
        System.out.println("Partida criada! O jogo irá começar...");

        entityManager.close();
        JPAUtil.shutdown();
    }
}
