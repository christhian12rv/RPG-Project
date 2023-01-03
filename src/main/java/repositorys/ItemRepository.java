package repositorys;

import entitys.Item;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ItemRepository extends BaseRepository<Item> {
    
    public ItemRepository(EntityManager entityManager) {
        super(Item.class, entityManager);
    }

}