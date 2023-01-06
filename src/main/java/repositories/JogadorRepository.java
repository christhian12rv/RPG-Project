package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Jogador;

import java.util.List;

public class JogadorRepository extends BaseRepository<Jogador> {

    public JogadorRepository(EntityManager entityManager) {
        super(Jogador.class, entityManager);
    }

}