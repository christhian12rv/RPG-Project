package util;

import entitys.Habilidade;
import enums.TipoAtributo;
import service.HabilidadeService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class HabilidadeUtil {
    private static HabilidadeService habilidadeService;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        habilidadeService = new HabilidadeService(entityManager);

        Habilidade habilidade = criarHabilidade();
        habilidadeService.save(habilidade);

        //List<Habilidade> habilidade = habilidadeService.findAll();
        //List<Habilidade> habilidades = habilidadeService.findAllRandomByPreRequisitosAndTipo(3,4, 9, 2, TipoAtributo.SABEDORIA);
        entityManager.close();
        JPAUtil.shutdown();
    }

    private static Habilidade criarHabilidade() {
        Scanner scanner = new Scanner(System.in);

        String nome;
        String descricao;
        String tipoAtributoStr;
        TipoAtributo tipoAtributo;
        int dano;
        int custo;
        int area;
        boolean dropavel;
        List<Integer> preRequisitos = new ArrayList<>();

        System.out.print("Nome: ");
        nome = scanner.nextLine();

        System.out.print("Descrição: ");
        descricao = scanner.nextLine();

        System.out.print("TipoAtributo: ");
        tipoAtributoStr = scanner.nextLine();
        tipoAtributo = TipoAtributo.valueOf(tipoAtributoStr);

        System.out.print("Dano: ");
        dano = scanner.nextInt();

        System.out.print("Custo: ");
        custo = scanner.nextInt();

        System.out.print("Area: ");
        area = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Dropável: ");
        dropavel = "S".equals(scanner.nextLine().toUpperCase());

        System.out.println("Pré Requisitos");
        System.out.print("Força: ");
        preRequisitos.add(scanner.nextInt());
        System.out.print("Destreza: ");
        preRequisitos.add(scanner.nextInt());
        System.out.print("Sabedoria: ");
        preRequisitos.add(scanner.nextInt());
        System.out.print("Defesa: ");
        preRequisitos.add(scanner.nextInt());

        Habilidade habilidade = new Habilidade(nome, descricao, tipoAtributo, dano, custo, area, dropavel, preRequisitos);

        return habilidade;
    }
}
