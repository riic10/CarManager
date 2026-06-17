package app.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import app.model.Car;
import app.service.CarService;
import app.web.dto.CarRequest;
import app.web.dto.CarResponse;

@RestController
public class CarController {
    private final CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/cars")
    public List<CarResponse> getAllCars() {
        return service.findAll().stream()
        .map(CarResponse::from)
        .toList();
    }

    @GetMapping("/cars/{id}")
    public CarResponse getById(@PathVariable Long id) {
        return CarResponse.from(service.getById(id));
    }

    @PostMapping("/cars")
    public ResponseEntity<CarResponse> create(@Valid @RequestBody CarRequest request) {
        Car saved = service.create(request.toEntity());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(saved.getID())
            .toUri();
        return ResponseEntity.created(location).body(CarResponse.from(saved));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
