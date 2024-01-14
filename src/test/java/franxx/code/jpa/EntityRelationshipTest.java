package franxx.code.jpa;

import franxx.code.jpa.entity.Credential;
import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.User;
import franxx.code.jpa.entity.Wallet;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityRelationshipTest {

    @Test
    void oneToOneCreate() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Credential credential = new Credential();
        credential.setId("001");
        credential.setEmail("me@me.com");
        credential.setPassword("password");
        entityManager.persist(credential);

        User user = new User();
        user.setId("001");
        user.setName("Mee");
        entityManager.persist(user);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneFind() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        User user = entityManager.find(User.class, "001");

        assertNotNull(user.getCredential());
        assertNotNull(user.getWallet());
        assertEquals("password", user.getCredential().getPassword());
        assertEquals("me@me.com", user.getCredential().getEmail());
        assertEquals(1_000_000_000L, user.getWallet().getBalance());
        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToOneJoinColumn() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        User user = entityManager.find(User.class, "001");

        Wallet wallet =  new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000_000L);
        entityManager.persist(wallet);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
