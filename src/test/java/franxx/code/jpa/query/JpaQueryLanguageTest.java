package franxx.code.jpa.query;

import franxx.code.jpa.entity.Brand;
import franxx.code.jpa.entity.Member;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JpaQueryLanguageTest {

    @Test
    void select() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(
                    brand.getId() + " : " + brand.getName()
            );
        }


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void whereClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Member> query = entityManager.createQuery(
                "select m from Member m where m.name.firstName = :fName and m.name.lastName = :lName",
                Member.class
        );

        query.setParameter("fName", "Mee");
        query.setParameter("lName", "LL");

        List<Member> resultList = query.getResultList();

        for (Member member : resultList) {
            System.out.println(
                    member.getId() + " : " + member.getFullName()
            );
        }

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
