import entitys.Monstro;
import enums.DificuldadeMonstro;
import service.MonstroService;
import util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
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

        entityManager.close();
        JPAUtil.shutdown();
    }
}
