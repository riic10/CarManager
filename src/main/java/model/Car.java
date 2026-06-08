package model;

import jakarta.persistence.*;
import org.json.JSONObject;

@Entity
@Table(name="cars")
// Creates a new Car object with fields representing the production year, make, model,
// category, and whether or not it's for sale or not.
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 100)
    private String make;

    @Column(nullable = false, length = 100)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Column(name = "for_sale", nullable = false)
    private boolean forSale;

    // Zero argument constructor for JPA
    protected Car() {}

    // EFFECTS: Constructs a Car with an ID number, the given year, make, model, category,
    // and whether it is for sale
    public Car(int year, String make, String model, Category category, boolean forSale) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.category = category;
        this.forSale = forSale;
    }

    public Long getID() {
        return this.id;
    }

    public int getYear() {
        return this.year;
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean getForSale() {
        return this.forSale;
    }

    // MODIFIES: this
    // EFFECTS: Sets the ID for the current Car
    public void setID(Long id) {
        this.id = id;
    }

    // MODIFIES: this
    // EFFECTS: Sets the Car as being for sale
    public void setForSale() {
        this.forSale = true;
    }

    // MODIFIES: this
    // EFFECTS: Sets the Car as being not for sale
    public void setNotForSale() {
        this.forSale = false;
    }

    // EFFECTS: Returns the given Car converted into a String
    public String toString() {
        return "ID: " + id + " -- " + year + " " + make + " " + model
            + " -- Category: " + category + " -- For sale?: " + forSale;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("year", year);
        json.put("make", make);
        json.put("model", model);
        json.put("category", category);
        json.put("forSale", forSale);
        return json;
    }
}
