package repositories;

import javax.persistence.EntityManager;

import entities.Jogador;

public class JogadorRepository extends BaseRepository<Jogador> {

    public JogadorRepository(EntityManager entityManager) {
        super(Jogador.class, entityManager);
    }

}