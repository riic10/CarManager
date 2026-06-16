package app.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }

    public Car create(Car car) {
        return repository.save(car);
    }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
