package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot entry point for the Car Manager REST API.
// Lives in the root `app` package; the model, repository, and web layers are
// nested beneath it (app.model, app.repository, app.web), so Spring's default
// component / entity / repository scanning finds them all with no explicit
// @EntityScan or @EnableJpaRepositories.
@SpringBootApplication
public class CarManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarManagerApplication.class, args);
    }
}