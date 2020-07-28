package fr.jhagai.consumerpublisher;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Repository
public class MyEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MyEntity> findUnlocked(int limit) {

        return entityManager.createQuery("SELECT e FROM MyEntity e",
                MyEntity.class).setLockMode(LockModeType.PESSIMISTIC_WRITE)
                //.setHint("javax.persistence.lock.timeout", 0)
                .setMaxResults(limit).getResultList();
    }

    public void save(MyEntity e) {
        this.entityManager.persist(e);
    }

    public void delete(MyEntity e) {
        this.entityManager.remove(e);
    }
}
