package repositorys;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BaseRepository<T> {
    private EntityManager entityManager;
    private Class<T> type;

    public BaseRepository(Class<T> type, EntityManager entityManager) {
        this.type = type;
        this.entityManager = entityManager;
    }

    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public List<T> findAll() {
        Query query = entityManager.createQuery("SELECT e FROM " + type.getName() + " e");
        List<T> entitys = query.getResultList();

        return entitys;
    }

    public T findById(Integer id) {
        T entity = entityManager.find(type, id);

        return entity;
    }

    public int deleteById(Integer id) {
        T entity = entityManager.find(type, id);

        if (entity == null)
            return 0;

        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
        return 1;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}