package utils;

import entitys.Arma;
import enums.RaridadeArma;
import enums.TipoAtributo;
import repositorys.ArmaRepository;
import repositorys.JogadorRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class ArmaUtil {
    private static ArmaRepository armaRepository;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        armaRepository = new ArmaRepository(entityManager);

        //Arma armaCriada = criarArma();
        //armaRepository.save(armaCriada);

        List<Arma> armaS = armaRepository.findAll();
        Arma armaById = armaRepository.findById(1);
        Arma armaByRaridadeAndTipoAtributo = armaRepository.findOneByRaridadeAndTipoAtributo(TipoAtributo.SABEDORIA);

        int armaDeletada = armaRepository.deleteById(1);

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
