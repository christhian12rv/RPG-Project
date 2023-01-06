package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Personagem;

import java.util.List;

public class PersonagemRepository extends BaseRepository<Personagem>{

    public PersonagemRepository(EntityManager entityManager) {
        super(Personagem.class, entityManager);
    }
}