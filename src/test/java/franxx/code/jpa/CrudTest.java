package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrudTest {

    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    @Test
    void insert() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        assertNotNull(transaction);
        transaction.begin();

        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Franxx 2");

        assertNotNull(customer);
        entityManager.persist(customer);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void find() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        assertNotNull(transaction);
        transaction.begin();

        Customer customer = entityManager.find(Customer.class, "1");

        assertNotNull(customer);
        assertEquals("1", customer.getId());
        assertEquals("Franxx", customer.getName());

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void update() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        assertNotNull(transaction);

        transaction.begin();

        Customer customer = entityManager.find(Customer.class, "2");
        customer.setName("Zero Two");
        entityManager.merge(customer);

        transaction.commit();


        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void remove() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        assertNotNull(transaction);

        transaction.begin();

        Customer customer = entityManager.find(Customer.class, "2");
        entityManager.remove(customer);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
