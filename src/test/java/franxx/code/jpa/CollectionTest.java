package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.Member;
import franxx.code.jpa.entity.embedded.Name;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {

    @Test
    void create() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Collection<String> hobbies = new HashSet<>(List.of("Coding", "Learn some.."));
        Member member = new Member();
        member.setEmail("test@mail.mm");
        member.setName(Name.of("Mr", "Bung", "Yellow", "N"));
        member.setHobbies(hobbies);

        entityManager.persist(member);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void update() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member = entityManager.find(Member.class, 2);

        member.getHobbies().add("Sleep");

        entityManager.merge(member);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();

    }
}
