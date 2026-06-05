package persistence;

import model.Car;
import model.Category;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCar(int year, String make, String model, Category category, boolean forSale, Car car) {
        assertEquals(year, car.getYear());
        assertEquals(make, car.getMake());
        assertEquals(model, car.getModel());
        assertEquals(category, car.getCategory());
        assertEquals(forSale, car.getForSale());
    }
}