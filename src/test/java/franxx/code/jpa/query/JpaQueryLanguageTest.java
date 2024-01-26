package franxx.code.jpa.query;

import franxx.code.jpa.entity.*;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;

import java.util.List;

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
            System.out.println(brand.getId() + " : " + brand.getName());
        }


        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void limitOffSet() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.id", Brand.class);

        query.setMaxResults(10).setFirstResult(10).getResultList().forEach(brand -> System.out.println(brand.getId()));

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Product> query = entityManager.createQuery("select p from Product p join p.brand b where b.name = :bname", Product.class);

        query.setParameter("bname", "Samsung");

        List<Product> products = query.getResultList();

        // list semua product dengan brand name samsung
        for (Product product : products) {
            System.out.println(product.getId() + " : " + product.getName() + " - " + product.getBrand().getName());
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

        TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.name.firstName = :fName and m.name.lastName = :lName", Member.class);

        query.setParameter("fName", "Mee");
        query.setParameter("lName", "LL");

        List<Member> resultList = query.getResultList();

        for (Member member : resultList) {
            System.out.println(member.getId() + " : " + member.getFullName());
        }

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void orderByClause() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name desc", Brand.class);

        query.getResultList().forEach(brand -> {
            System.out.println(brand.getName());
        });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertRandomBrand() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        for (int i = 0; i < 100; i++) {
            Brand brand = new Brand();
            brand.setId(String.valueOf(i));
            brand.setName("Brand " + i);
            entityManager.persist(brand);
        }

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void selectSomeFieldConstructor() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<SimpleBrand> query = entityManager.createQuery("select new franxx.code.jpa.entity.SimpleBrand(b.id, b.name) from Brand b", SimpleBrand.class);

        query.getResultList().forEach(simpleBrand -> System.out.println(simpleBrand.getId() + " - " + simpleBrand.getName()));

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinClauseFetch() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        // eager join
        TypedQuery<User> query = entityManager.createQuery("select u from User u join fetch u.likes l", User.class);

        List<User> resultList = query.getResultList();
        resultList.forEach(user -> {
            System.out.println(user.getName() + " is liked: ");
            user.getLikes().forEach(product -> System.out.println("product : " + product.getName()));
        });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void selectSomeField() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select b.id, b.name from Brand b where b.id = :id", Object[].class);

        query.setParameter("id", "samsung").getResultList().forEach(objects -> System.out.println(objects[0] + " - " + objects[1]));

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Brand> namedQuery = entityManager.createNamedQuery("Brand.findAllByName", Brand.class);

        namedQuery.setParameter("name", "Brand 1").getResultList().forEach(brand -> System.out.println(brand.getName()));

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void aggregateQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery(
                "select min(p.price), max(p.price), avg(p.price) from Product p",
                Object[].class
        );

        Object[] result = query.getSingleResult();
        System.out.println("min: " + result[0]);
        System.out.println("max: " + result[1]);
        System.out.println("avg: " + result[2]);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void aggregateQueryGroupByHaving() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery(
                "select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > :min",
                Object[].class
        );

        query.setParameter("min", 1)
                .getResultList()
                .forEach(
                        objects -> {
                            System.out.println("brand: " + objects[0]);
                            System.out.println("min: " + objects[1]);
                            System.out.println("max: " + objects[2]);
                            System.out.println("avg: " + objects[3]);
                        }
                );

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nativeQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Query query = entityManager.createNativeQuery(
                "select * from brands b where b.created_at is not null",
                Brand.class
        );

        List<Brand> resultList = query.getResultList();

        resultList.forEach(brand -> {
            System.out.println(brand.getName());
        });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedNativeQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        TypedQuery<Brand> query = entityManager.createNamedQuery("Brand.native.findAll", Brand.class);

        query.getResultList()
                .forEach(brand -> {
                    System.out.println("--------");
                    System.out.println(brand.getId());
                    System.out.println(brand.getName());
                    System.out.println(brand.getCreatedAt());
                    System.out.println("--------");
                });

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nonQuery() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Query query = entityManager.createQuery(
                "update Brand b set b.name = :name where b.id = :id"
        );

        int update = query.setParameter("name", "Nokia the update")
                .setParameter("id", "nokia")
                .executeUpdate();

        System.out.println("Success impact: " + update);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }


}
