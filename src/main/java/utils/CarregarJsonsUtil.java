package utils;

import javax.persistence.EntityManager;

import repositories.ArmaRepository;
import repositories.HabilidadeRepository;
import repositories.HistoriaRepository;
import repositories.MiniHistoriaRepository;
import repositories.MonstroRepository;
import utils.EntitiesUtils.ArmaUtil;
import utils.EntitiesUtils.HabilidadeUtil;
import utils.EntitiesUtils.HistoriaUtil;
import utils.EntitiesUtils.MonstroUtil;

public class CarregarJsonsUtil {
	public static void main(String[] args) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		ArmaRepository armaRepository = new ArmaRepository(entityManager);
		HabilidadeRepository habilidadeRepository = new HabilidadeRepository(entityManager);
		HistoriaRepository historiaRepository = new HistoriaRepository(entityManager);
		MiniHistoriaRepository miniHistoriaRepository = new MiniHistoriaRepository(entityManager);
		MonstroRepository monstroRepository = new MonstroRepository(entityManager);
		HabilidadeRepository habilidadeRepository2 = new HabilidadeRepository(entityManager);

		ArmaUtil armaUtil = new ArmaUtil();
		HabilidadeUtil habilidadeUtil = new HabilidadeUtil();
		HistoriaUtil historiaUtil = new HistoriaUtil();
		MonstroUtil monstroUtil = new MonstroUtil();

		armaUtil.setArmaRepository(armaRepository);
		habilidadeUtil.setHabilidadeRepository(habilidadeRepository);
		historiaUtil.setHistoriaRepository(historiaRepository);
		historiaUtil.setMiniHistoriaRepository(miniHistoriaRepository);
		monstroUtil.setMonstroRepository(monstroRepository);
		monstroUtil.setHabilidadeRepository(habilidadeRepository2);

		armaUtil.lerJsonESalvar();
		habilidadeUtil.lerJsonESalvar();
		historiaUtil.lerJsonESalvar();
		monstroUtil.lerJsonESalvar();

		entityManager.close();
    	JPAUtil.shutdown();
	}
}
