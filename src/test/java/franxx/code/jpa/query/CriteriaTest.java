package franxx.code.jpa.query;

import franxx.code.jpa.entity.Brand;
import franxx.code.jpa.entity.Product;
import franxx.code.jpa.entity.SimpleBrand;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
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

    @Test
    void criteriaJoinClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand"); // .fetch; for eager

        // select p from Product p join p.brand b where b.id = 'samsung';
        criteria.select(p).where(
                builder.equal(b.get("id"), "samsung")
        );

        entityManager.createQuery(criteria)
                .getResultList()
                .forEach(product -> {
                    System.out.println(product.getName());
                });


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaNamedParameter() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);

        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand"); // .fetch; for eager

        ParameterExpression<String> parameterBrand = builder.parameter(String.class, "brand");

        // select p from Product p join p.brand b where b.id = 'samsung';
        criteria.select(p).where(
                builder.equal(b.get("id"), parameterBrand)
        );

        entityManager.createQuery(criteria)
                .setParameter(parameterBrand, "samsung")
                .getResultList()
                .forEach(product -> {
                    System.out.println(product.getName());
                });


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaAggregateQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > 2
        criteria.select(builder.array(
                b.get("id"),
                builder.min(p.get("price")),
                builder.max(p.get("price")),
                builder.avg(p.get("price"))
        )).groupBy(b.get("id")).having(builder.greaterThan(builder.min(p.get("price")), 2));

        entityManager.createQuery(criteria)
                .getResultList()
                .forEach(objects -> {
                    System.out.println("============");
                    System.out.println("Brand: " + objects[0]);
                    System.out.println("min: " + objects[1]);
                    System.out.println("max: " + objects[2]);
                    System.out.println("avg: " + objects[3]);
                });


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaNonQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Brand> criteria = builder.createCriteriaUpdate(Brand.class);

        Root<Brand> b = criteria.from(Brand.class);

        criteria.set(b.get("name"), "Nokia")
                .set(b.get("description"), "PT Nokia")
                .where(builder.equal(b.get("id"), "nokia"));

        int update = entityManager.createQuery(criteria).executeUpdate();
        System.out.println("impact " + update);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


}
