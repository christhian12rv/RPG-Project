package utils;

import entitys.Historia;
import entitys.MiniHistoria;
import entitys.Monstro;
import enums.TipoMonstro;
import repositorys.HabilidadeRepository;
import repositorys.HistoriaRepository;
import repositorys.MiniHistoriaRepository;
import repositorys.MonstroRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoriaUtil {
    private static HistoriaRepository historiaRepository;
    private static MiniHistoriaRepository miniHistoriaRepository;
    private static MiniHistoriaUtil miniHistoriaUtil;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        historiaRepository = new HistoriaRepository(entityManager);
        miniHistoriaRepository = new MiniHistoriaRepository(entityManager);
        HabilidadeRepository habilidadeRepository = new HabilidadeRepository(entityManager);
        miniHistoriaUtil = new MiniHistoriaUtil();

        miniHistoriaUtil.setMiniHistoriaRepository(miniHistoriaRepository);


        Historia historia = criarHistoria();
        historiaRepository.save(historia);

        //List<Monstro> monstroS = monstroRepository.findAll();
        //Monstro monstroById = monstroRepository.findById(1);
        //Monstro monstroByRaridadeAndTipoAtributo = monstroRepository.findOneByRaridadeAndTipoAtributo(TipoAtributo.SABEDORIA);

        //int monstroDeletado = monstroRepository.deleteById(1);

        entityManager.close();
        JPAUtil.shutdown();
    }

    public static Historia criarHistoria() {
        Scanner scanner = new Scanner(System.in);
        TipoMonstro tipoMonstro = null;
        String descricao = "";
        String previaDescricao = "";
        int qtdMiniHistorias = 0;
        List<MiniHistoria> miniHistorias = new ArrayList<>();

        System.out.print("Tipo Monstro: ");
        tipoMonstro = TipoMonstro.valueOf(scanner.nextLine());

        System.out.print("Descrição: ");
        descricao = scanner.nextLine();

        System.out.print("Prévia descrição: ");
        previaDescricao = scanner.nextLine();

        System.out.print("Quantidade de Mini Histórias: ");
        qtdMiniHistorias = scanner.nextInt();

        for (int i = 0; i < qtdMiniHistorias; i++) {
            System.out.println("Mini História 1");
            miniHistorias.add(miniHistoriaUtil.criarMiniHistoria());
            System.out.println("");
        }

        Historia historia = new Historia(tipoMonstro, descricao, previaDescricao, miniHistorias);

        return historia;
    }
    
    public Historia historiaRandomica() {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        int escolha = 0;
        
        System.out.println("Histórias");

        List<Historia> historias = null;
        historias = historiaRepository.findAllRandom();


        for (Historia historia: historias) {
            System.out.println("[" + i + "]");
            System.out.println(historia);
            i++;
        }

        System.out.print("Digite uma historia: ");
        escolha = scanner.nextInt();

        while (escolha <= 0 || escolha > historias.size()) {
            System.out.print("Historia incorreta. Digite novamente: ");
            escolha = scanner.nextInt();
        }

        return historias.get(escolha - 1);
    }

    public HistoriaRepository getHistoriaRepository() {
        return historiaRepository;
    }

    public void setHistoriaRepository(HistoriaRepository historiaRepository) {
        this.historiaRepository = historiaRepository;
    }
}