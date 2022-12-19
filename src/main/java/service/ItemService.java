package service;

import entitys.Item;
import entitys.Monstro;
import entitys.Partida;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ItemService {
    private EntityManager entityManager;

    public ItemService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Item item) {
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    public List<Item> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Item m");
        List<Item> itens = query.getResultList();

        return itens;
    }

    public Item findById(Integer id) {
        Item item = entityManager.find(Item.class, id);

        return item;
    }

    public int deleteById(Integer id) {
        Item item = entityManager.find(Item.class, id);

        if (item == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(item);
        entityManager.getTransaction().commit();
        return 1;
    }
}