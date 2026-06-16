package app.web.dto;

import app.model.Car;
import app.model.Category;

public record CarResponse(
    Long id,
    int year,
    String make,
    String model,
    Category category,
    boolean forSale ) {
        
    public static CarResponse from (Car car) {
        return new CarResponse(
            car.getID(),
            car.getYear(),
            car.getMake(),
            car.getModel(),
            car.getCategory(),
            car.getForSale());
    }
}
