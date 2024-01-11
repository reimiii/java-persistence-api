package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.Member;
import franxx.code.jpa.entity.embedded.Name;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedTest {

    @Test
    void create() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Name name = new Name();
        name.setTitle("MR");
        name.setFirstName("Mee");
        name.setMiddleName("PP");
        name.setLastName("LL");

        Member member = new Member();
        member.setEmail("mail@mail.com");
        member.setName(name);

        entityManager.persist(member);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
