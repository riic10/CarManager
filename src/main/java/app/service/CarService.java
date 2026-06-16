package app.service;

import java.util.List;

import org.springframework.stereotype.Service;


import app.model.Car;
import app.repository.CarRepository;

@Service
public class CarService {
    private final CarRepository repository;
    
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> findAll() {
        return repository.findAll();
    }
}
