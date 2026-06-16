package app.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import app.model.Car;
import app.model.Category;

public record CarRequest(
    @Min(1885) @Max(2100) int year,
    @NotBlank String make,
    @NotBlank String model,
    @NotNull Category category,
    boolean forSale) {

    public Car toEntity() {
        return new Car(year, make, model, category, forSale);
    }
}
