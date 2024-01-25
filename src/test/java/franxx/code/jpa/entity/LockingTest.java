package franxx.code.jpa.entity;

import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class LockingTest {

    @Test
    void optimisticLocking() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = new Brand();
        brand.setId("nokia");
        brand.setName("Nokia test");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticDemoOne() throws InterruptedException {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia demo one update..");
        brand.setUpdatedAt(LocalDateTime.now());
        Thread.sleep(10 * 1000L);

        entityManager.merge(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticDemoTwo() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia demo two.. update..");
        brand.setUpdatedAt(LocalDateTime.now());

        entityManager.merge(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void pessimisticDemoOne() throws InterruptedException {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia demo one update.. pessimistic");
        brand.setUpdatedAt(LocalDateTime.now());
        Thread.sleep(10 * 1000L);

        entityManager.merge(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void pessimisticDemoTwo() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia demo two.. update..pessimistic mmmhh increment");
        brand.setUpdatedAt(LocalDateTime.now());

        entityManager.merge(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}
