package app.web.dto;

import app.model.Car;
import app.model.Category;

public record CarRequest (
    int year,
    String make,
    String model,
    Category category,
    boolean forSale) {

    public Car toEntity() {
    return new Car(year, make, model, category, forSale);
    }
}
