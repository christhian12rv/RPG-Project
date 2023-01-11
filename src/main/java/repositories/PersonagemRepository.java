package repositories;

import javax.persistence.EntityManager;

import entities.Personagem;

public class PersonagemRepository extends BaseRepository<Personagem>{

    public PersonagemRepository(EntityManager entityManager) {
        super(Personagem.class, entityManager);
    }
}