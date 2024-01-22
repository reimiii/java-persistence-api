package franxx.code.jpa;

import franxx.code.jpa.entity.*;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

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

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000_000L);
        entityManager.persist(wallet);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyInsert() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = new Brand();
        brand.setId("samsung");
        brand.setName("Samsung");
        entityManager.persist(brand);

        Product p1 = new Product();
        p1.setId("001");
        p1.setName("S 1");
        p1.setBrand(brand);
        p1.setPrice(10L);
        entityManager.persist(p1);

        Product p2 = new Product();
        p2.setId("002");
        p2.setName("S 2");
        p2.setBrand(brand);
        p2.setPrice(21L);
        entityManager.persist(p2);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void oneToManyFind() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Brand brand = entityManager.find(Brand.class, "samsung");

        assertNotNull(brand.getProducts());
        assertEquals(2, brand.getProducts().size());

        brand.getProducts().forEach(product -> System.out.println(product.getName()));


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


    @Test
    void manyToManyInsert() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        User user = entityManager.find(User.class, "001");

        Collection<Product> productCollection = new HashSet<>();
        productCollection.add(entityManager.find(Product.class, "001"));
        productCollection.add(entityManager.find(Product.class, "002"));

        user.setLikes(productCollection);

        entityManager.merge(user);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void manyToManyUpdate() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        User user = entityManager.find(User.class, "001");

        // my version
//        Product next = user.getLikes().iterator().next();
//        System.out.println(next.getName());
//        System.out.println(next.getId());

        Product product = null;
        for (Product like : user.getLikes()) {
            product = like;
            break;
        }

        user.getLikes().remove(product);
        entityManager.merge(user);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


    @Test
    void fetch() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Product product = entityManager.find(Product.class, "001");
//        Brand brand = entityManager.find(Brand.class, "samsung");
        assertNotNull(product.getBrand().getName());

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
