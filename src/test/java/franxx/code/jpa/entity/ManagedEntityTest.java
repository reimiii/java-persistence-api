package franxx.code.jpa.entity;

import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ManagedEntityTest {

    @Test
    void create() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        //
        // unmanaged entity
        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");

        entityManager.persist(brand); // manage entity

        brand.setName("Apple International");
        // entityManager.merge(brand);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void detach() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        //
        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        entityManager.detach(brand); // unmanaged entity
        brand.setName("Apple Indonesia");

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void find() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        //
        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");
        brand.setName("Apple Indonesia");

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void afterTransaction() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        //
        // managed entity
        Brand brand = entityManager.find(Brand.class, "apple");

        transaction.commit();
        entityManager.close();

        brand.setName("Apple Indonesia");

        entityManagerFactory.close();
    }


}
