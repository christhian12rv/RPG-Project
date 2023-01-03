package utils;

import entitys.Monstro;
import enums.DificuldadeMonstro;
import enums.TipoMonstro;
import repositorys.HabilidadeRepository;
import repositorys.MonstroRepository;
import services.HabilidadeService;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class MonstroUtil {
    private static MonstroRepository monstroRepository;

    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        monstroRepository = new MonstroRepository(entityManager);
        HabilidadeRepository habilidadeRepository = new HabilidadeRepository(entityManager);

        Monstro monstroCriada = criarMonstro(habilidadeRepository);
        monstroRepository.save(monstroCriada);

        //List<Monstro> monstroS = monstroRepository.findAll();
        //Monstro monstroById = monstroRepository.findById(1);
        //Monstro monstroByRaridadeAndTipoAtributo = monstroRepository.findOneByRaridadeAndTipoAtributo(TipoAtributo.SABEDORIA);

        //int monstroDeletado = monstroRepository.deleteById(1);

        entityManager.close();
        JPAUtil.shutdown();
    }

    private static Monstro criarMonstro(HabilidadeRepository habilidadeRepository) {
        Scanner scanner = new Scanner(System.in);
        String ascii = "";
        DificuldadeMonstro dificuldadeMonstro = null;
        TipoMonstro tipoMonstro = null;

        System.out.print("Ascii: ");
        ascii = scanner.nextLine();

        System.out.print("Dificuldade Monstro: ");
        dificuldadeMonstro = DificuldadeMonstro.valueOf(scanner.nextLine());

        System.out.print("Tipo Monstro:");
        tipoMonstro = TipoMonstro.valueOf(scanner.nextLine());

        PersonagemUtil personagemUtil = new PersonagemUtil();
        HabilidadeService habilidadeService = new HabilidadeService();
        personagemUtil.setHabilidadeService(habilidadeService);
        habilidadeService.setHabilidadeRepository(habilidadeRepository);

        Monstro monstro = new Monstro(personagemUtil.criarPersonagem(), ascii, dificuldadeMonstro, tipoMonstro);

        return monstro;
    }
}
