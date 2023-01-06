package repositories;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entities.Item;

import java.util.List;

public class ItemRepository extends BaseRepository<Item> {
    
    public ItemRepository(EntityManager entityManager) {
        super(Item.class, entityManager);
    }

}