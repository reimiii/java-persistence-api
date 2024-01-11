package franxx.code.jpa;

import franxx.code.jpa.entity.Customer;
import franxx.code.jpa.entity.Image;
import franxx.code.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LargeObjectTest {

    @Test
    void uploadImg() throws IOException {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Image image = new Image();
        image.setName("SS");
        image.setDescription("Desc");

        byte[] bytes = Files.readAllBytes(Path.of(getClass().getResource("/images/mag.png").getPath()));
        assertNotNull(bytes);
        image.setImage(bytes);

        entityManager.persist(image);

        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
