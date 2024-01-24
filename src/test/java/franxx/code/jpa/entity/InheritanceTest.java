package franxx.code.jpa.entity;

import franxx.code.jpa.entity.embedded.Name;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InheritanceTest {

    @Test
    void singleTableInsert() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Employee employee = new Employee();
        employee.setId("01");
        employee.setName("Name 1");

        Manager manager = new Manager();
        manager.setId("02");
        manager.setName("Name Manager 1");
        manager.setTotalEmployee(10);

        VicePresident vicePresident = new VicePresident();
        vicePresident.setId("03");
        vicePresident.setName("Name VP 1");
        vicePresident.setTotalManager(5);

        entityManager.persist(employee);
        entityManager.persist(manager);
        entityManager.persist(vicePresident);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void singleTableFind() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Employee employee = entityManager.find(Employee.class, "01");
        assertEquals("Name 1", employee.getName());

        Employee employee1 = entityManager.find(Employee.class, "03");
        VicePresident vicePresident = (VicePresident) employee1;
        assertEquals("Name VP 1", vicePresident.getName());

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


    @Test
    void joinedTableInsert() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        PaymentGopay gopay = new PaymentGopay();
        gopay.setId("gopay-001");
        gopay.setGopayId("0001110011");
        gopay.setAmount(10_000L);

        PaymentCreditCard creditCard = new PaymentCreditCard();
        creditCard.setId("cc-001");
        creditCard.setAmount(111_000L);
        creditCard.setMaskedCard("455-555");
        creditCard.setBank("jago");

        entityManager.persist(gopay);
        entityManager.persist(creditCard);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


    @Test
    void joinedTableFind() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        PaymentGopay gopay = entityManager.find(PaymentGopay.class, "gopay-001");
        PaymentCreditCard creditCard = entityManager.find(PaymentCreditCard.class, "cc-001");

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

}