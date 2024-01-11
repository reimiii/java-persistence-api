package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.CustomerType;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnumTest {

    @Test
    void create() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        assertNotNull(transaction);
        transaction.begin();

        Customer customer = new Customer();
        customer.setId("005");
        customer.setName("pac");
        customer.setPrimaryEmail("pacman@mail.com");
        customer.setAge((byte) 19);
        customer.setMarried(false);
        customer.setType(CustomerType.PREMIUM);

        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
