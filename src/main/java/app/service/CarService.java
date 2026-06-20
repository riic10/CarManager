package app.service;

import java.util.List;

import org.springframework.stereotype.Service;


import app.model.Car;
import app.model.Category;
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

    public Car getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new CarNotFoundException(id));
    }

    public Car create(Car car) {
        return repository.save(car);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
       repository.deleteById(id);
    }

    public List<Car> find(Boolean forSale, Category category) {
        if (forSale != null && category != null) {
            return repository.findByForSaleAndCategory(forSale, category);
        }
        if (forSale != null) {
            return repository.findByForSale(forSale);
        }
        if (category != null) {
            return repository.findByCategory(category);
        }
        return repository.findAll();
    }
}
