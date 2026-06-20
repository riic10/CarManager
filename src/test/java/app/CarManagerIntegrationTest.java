package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import app.model.Car;
import app.model.Category;
import app.service.CarService;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
public class CarManagerIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    CarService service;

    @Test
    void createAndRetrieve_roundTripsThroughRealDb() {
        Car toSave = new Car(2026, "Porsche", "911 GT3", Category.SPORTSCAR, false);
        Car saved = service.create(toSave);

        assertNotNull(saved.getID());
        assertEquals("Porsche", service.getById(saved.getID()).getMake());
    }
}
