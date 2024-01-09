package franxx.code.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory entityManagerFactory = null;

    public static EntityManagerFactory getEntityManagerFactory() {

        if (entityManagerFactory == null) {

            entityManagerFactory = Persistence.createEntityManagerFactory("JPA");

        }

        return entityManagerFactory;
    }
}
