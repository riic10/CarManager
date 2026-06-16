package app.web;

import app.web.dto.CarResponse;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.repository.CarRepository;

@RestController
public class CarController {
    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cars")
    public List<CarResponse> getAllCars() {
        return repository.findAll().stream()
        .map(CarResponse::from)
        .toList();
    }
}
