package repositorys;

import entitys.Personagem;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PersonagemRepository extends BaseRepository<Personagem>{

    public PersonagemRepository(EntityManager entityManager) {
        super(Personagem.class, entityManager);
    }
}