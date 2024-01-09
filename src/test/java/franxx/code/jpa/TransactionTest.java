package franxx.code.jpa;

import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    void create() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();
            // do some database thing
            entityTransaction.commit();
        } catch (Throwable throwable) {
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}
