package app.repository;

import java.util.List;

import app.model.Car;
import app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByForSale(boolean forSale);

    List<Car> findByCategory(Category category);

    List<Car> findByForSaleAndCategory(boolean forSale, Category category);
}