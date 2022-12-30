package util;

import entitys.Arma;
import entitys.Jogador;
import enums.RaridadeArma;
import enums.TipoAtributo;
import service.ArmaService;
import service.JogadorService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ArmaUtil {
    private static ArmaService armaService;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        armaService = new ArmaService(entityManager);
        JogadorService jogadorService = new JogadorService(entityManager);

        //Arma arma = criarArma();
        //armaService.save(arma);

        //List<Arma> arma = armaService.findAll();
        Arma armas = armaService.findByRaridadeAndByTipoAtributo(TipoAtributo.SABEDORIA);

        entityManager.close();
        JPAUtil.shutdown();
    }

    private static Arma criarArma() {
        Scanner scanner = new Scanner(System.in);
        String nome = "";
        String descricao = "";
        RaridadeArma raridadeArma = null;
        int qtdDados = 0;
        int tipoDado = 0;
        int adicional = 0;
        TipoAtributo tipoAtributo = null;

        System.out.print("Nome: ");
        nome = scanner.nextLine(); 
        
        System.out.print("Descrição: ");
        descricao = scanner.nextLine();

        System.out.print("Raridade da Arma:");
        raridadeArma = RaridadeArma.valueOf(scanner.nextLine());
    
        System.out.print("Quantidade de dados: ");
        qtdDados = scanner.nextInt();

        System.out.print("Tipo de dado: ");
        tipoDado = scanner.nextInt();

        System.out.print("Adicional: ");
        adicional = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Tipo de atributo: ");
        tipoAtributo = TipoAtributo.valueOf(scanner.nextLine());

        Arma arma = new Arma(nome, descricao, raridadeArma, qtdDados, tipoDado, adicional, tipoAtributo);
    
        return arma;
    }
}
