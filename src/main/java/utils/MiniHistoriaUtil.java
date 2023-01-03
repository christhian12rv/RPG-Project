package utils;

import entitys.MiniHistoria;
import enums.DificuldadeMonstro;
import enums.TipoResultadoMiniHistoria;
import repositorys.MiniHistoriaRepository;

import java.util.Scanner;

public class MiniHistoriaUtil {
    private MiniHistoriaRepository miniHistoriaRepository;

    public MiniHistoria criarMiniHistoria() {
        Scanner scanner = new Scanner(System.in);
        String descricao = "";
        DificuldadeMonstro dificuldade = null;
        int miniHistoriaEscolhaOpostaId = 0;
        MiniHistoria miniHistoriaEscolhaOposta = null;
        String resultadoEscolha = "";
        TipoResultadoMiniHistoria tipoResultadoMiniHistoria = TipoResultadoMiniHistoria.NENHUM;
        int danoCura = 0;
        String desejaCriarMiniHistoriaOposta = "";
        MiniHistoria miniHistoria = null;

        System.out.print("Descrição: ");
        descricao = scanner.nextLine();

        System.out.print("Dificuldade Monstro: ");
        dificuldade = DificuldadeMonstro.valueOf(scanner.nextLine());

        System.out.print("Deseja criar uma miniHistória oposta (S ou N): ");
        desejaCriarMiniHistoriaOposta = scanner.nextLine();

        if ("N".equalsIgnoreCase(desejaCriarMiniHistoriaOposta)) {
            miniHistoria = new MiniHistoria(descricao, dificuldade,  miniHistoriaEscolhaOposta, resultadoEscolha, tipoResultadoMiniHistoria, danoCura);
            miniHistoriaRepository.save(miniHistoria);
            return miniHistoria;
        }

        System.out.println("Descrição do Resultado 1: ");
        resultadoEscolha = scanner.nextLine();

        System.out.print("Tipo Resultado Mini História 1: ");
        tipoResultadoMiniHistoria = TipoResultadoMiniHistoria.valueOf(scanner.nextLine());

        System.out.print("Dano / Cura do resultado 1: ");
        danoCura = scanner.nextInt();

        miniHistoriaEscolhaOposta = criarMiniHistoriaOposta(dificuldade);

        miniHistoria = new MiniHistoria(descricao, dificuldade,  miniHistoriaEscolhaOposta, resultadoEscolha, tipoResultadoMiniHistoria, danoCura);
        miniHistoriaRepository.save(miniHistoria);

        return miniHistoria;
    }

    public MiniHistoria criarMiniHistoriaOposta(DificuldadeMonstro dificuldade) {
        Scanner scanner = new Scanner(System.in);
        String descricao = "";
        String resultadoEscolha = "";
        TipoResultadoMiniHistoria tipoResultadoMiniHistoria = TipoResultadoMiniHistoria.NENHUM;
        int danoCura = 0;

        System.out.print("Descrição 2: ");
        descricao = scanner.nextLine();

        System.out.println("Descrição do Resultado 2: ");
        resultadoEscolha = scanner.nextLine();

        System.out.print("Tipo Resultado Mini História 2: ");
        tipoResultadoMiniHistoria = TipoResultadoMiniHistoria.valueOf(scanner.nextLine());

        System.out.print("Dano / Cura do resultado 2: ");
        danoCura = scanner.nextInt();

        MiniHistoria miniHistoria = new MiniHistoria(descricao, dificuldade,  null, resultadoEscolha, tipoResultadoMiniHistoria, danoCura);
        miniHistoriaRepository.save(miniHistoria);

        return miniHistoria;
    }

    public MiniHistoriaRepository getMiniHistoriaRepository() {
        return miniHistoriaRepository;
    }

    public void setMiniHistoriaRepository(MiniHistoriaRepository miniHistoriaRepository) {
        this.miniHistoriaRepository = miniHistoriaRepository;
    }
}
