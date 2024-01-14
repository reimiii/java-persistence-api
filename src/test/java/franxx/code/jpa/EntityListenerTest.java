package franxx.code.jpa;

import franxx.code.jpa.entity.Category;
import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.Member;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityListenerTest {

    @Test
    void create() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Category category = new Category();
        category.setName("Contoh");

        entityManager.persist(category);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void createEntity() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = entityManager.find(Member.class, 1);
        assertEquals("MR. Mee", member.getFullName());

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
