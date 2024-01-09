package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ColumnTest {

    @Test
    void create() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Customer customer = new Customer();
        customer.setId("002");
        customer.setName("Darling in FranXX");
        customer.setPrimaryEmail("franxx@mail.com");

        entityManager.persist(customer);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
