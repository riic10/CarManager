package app.web;

import app.web.dto.CarResponse;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.service.CarService;

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
}
