package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot entry point for the Car Manager REST API.
// Placed in its own `app` package (not a root repackage of the existing
// model/persistence/ui code) during the migration; Spring components are
// pointed at the packages they need to scan as they are added.
// Runs alongside the legacy Swing UI (ui.Main) during the migration.
@SpringBootApplication
public class CarManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarManagerApplication.class, args);
    }
}
