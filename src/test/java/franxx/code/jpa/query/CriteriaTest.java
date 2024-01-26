package franxx.code.jpa.query;

import franxx.code.jpa.entity.Brand;
import franxx.code.jpa.entity.SimpleBrand;
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

    @Test
    void criteriaQueryNonEntity() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = builder.createQuery(Object[].class);

        Root<Brand> b = criteriaQuery.from(Brand.class);

        // select b.id b.name from Brand b; in jpa query
        criteriaQuery.select(builder.array(b.get("id"), b.get("name")));

        entityManager.createQuery(criteriaQuery)
                .getResultList()
                .forEach(objects -> {
                    System.out.println("-----");
                    System.out.println("id: " + objects[0]);
                    System.out.println("name: " + objects[1]);
                });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryNonEntityConstructor() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SimpleBrand> criteriaQuery = builder.createQuery(SimpleBrand.class);

        Root<Brand> b = criteriaQuery.from(Brand.class);
        criteriaQuery.select(builder.construct(SimpleBrand.class, b.get("id"), b.get("name")));

        entityManager.createQuery(criteriaQuery)
                .getResultList()
                .forEach(simpleBrand -> {
                    System.out.println(simpleBrand.getId() + " - " + simpleBrand.getName());
                });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaWhereClause() { // default is using AND

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = builder.createQuery(Brand.class);

        Root<Brand> b = criteriaQuery.from(Brand.class);

        criteriaQuery.select(b).where(
                builder.equal(b.get("id"), "nokia"),
                builder.isNotNull(b.get("createdAt"))
        );

        entityManager.createQuery(criteriaQuery)
                .getResultList()
                .forEach(
                        brand -> System.out.println(brand.getId())
                );


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaWhereClauseUsingOR() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteriaQuery = builder.createQuery(Brand.class);

        Root<Brand> b = criteriaQuery.from(Brand.class);

        criteriaQuery.select(b).where(
                builder.or(
                        builder.equal(b.get("id"), "samsung"),
                        builder.equal(b.get("id"), "nokia")
                )
        );

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
