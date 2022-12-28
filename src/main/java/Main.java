import entitys.Jogador;
import entitys.Monstro;
import enums.DificuldadeMonstro;
import service.*;
import util.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        /*
        MonstroService monstroService = new MonstroService(entityManager);


        Monstro monstro = new Monstro();
        monstro.setDificuldade(DificuldadeMonstro.INICIANTE);

        monstroService.save(monstro);

        List<Monstro> monstros = monstroService.findAll();

        for (Monstro m: monstros) {
            System.out.println("Id = " + m.getId() + "  Dificuldade = " + m.getDificuldade());
        }

        monstro = monstroService.findById(4);
        if (monstro == null)
            System.out.println("Esse monstro não existe");
        else
            System.out.println("\nId = " + monstro.getId() + "  Dificuldade = " + monstro.getDificuldade());

        System.out.println(monstroService.deleteById(4));
        */

        //TimeUnit.NANOSECONDS.sleep( Long.MAX_VALUE - 1);

        Scanner scanner = new Scanner(System.in);
        int qtdJogadores = 0;
        int i = 0;
        List<Jogador> jogadores = null;

        PersonagemUtil personagemUtil = new PersonagemUtil();
        JogadorUtil jogadorUtil = new JogadorUtil();
        InventarioUtil inventarioUtil = new InventarioUtil();
        ItemUtil itemUtil = new ItemUtil();

        ArmaService armaService = new ArmaService(entityManager);
        JogadorService jogadorService = new JogadorService(entityManager);
        HabilidadeService habilidadeService = new HabilidadeService(entityManager);
        InventarioService inventarioService = new InventarioService(entityManager);
        ItemService itemService = new ItemService(entityManager);

        personagemUtil.setHabilidadeService(habilidadeService);

        jogadorUtil.setPersonagemUtil(personagemUtil);
        jogadorUtil.setArmaService(armaService);
        jogadorUtil.setInventarioUtil(inventarioUtil);
        jogadorUtil.setJogadorService(jogadorService);

        inventarioUtil.setInventarioService(inventarioService);
        inventarioUtil.setItemUtil(itemUtil);

        itemUtil.setItemService(itemService);

        System.out.print("Digite a quantidade de jogadores (entre 1 e 4): ");
        qtdJogadores = scanner.nextInt();

        while (qtdJogadores <= 0 || qtdJogadores > 4) {
            System.out.print("Quantidade inválida (entre 1 e 4). Digite novamente: ");
            qtdJogadores = scanner.nextInt();
        }

        jogadores = jogadorUtil.criarJogadores(qtdJogadores);

        System.out.println("Jogadores criados");

        

        entityManager.close();
        JPAUtil.shutdown();
    }
}
