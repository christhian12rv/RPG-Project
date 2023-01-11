package repositories;

import javax.persistence.EntityManager;

import entities.Item;

public class ItemRepository extends BaseRepository<Item> {
    
    public ItemRepository(EntityManager entityManager) {
        super(Item.class, entityManager);
    }

}