package franxx.code.jpa.query;

import franxx.code.jpa.entity.Brand;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CriteriaTest {

    @Test
    void criteriaQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = builder.createQuery(Brand.class);

        Root<Brand> b = criteriaQuery.from(Brand.class);
        criteriaQuery.select(b); // select b from Brand b; in jpa query

        entityManager.createQuery(criteriaQuery)
                .getResultList()
                .forEach(
                        brand -> System.out.println(brand.getId())
                );


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
