package model;

import org.json.JSONObject;

// Creates a new Car object with fields representing the production year, make, model,
// category, and whether or not it's for sale or not.
public class Car {
    private int id;
    private int year;
    private String make;
    private String model;
    private Category category;
    private boolean forSale;

    // EFFECTS: Constructs a Car with an ID number, the given year, make, model, category,
    // and whether it is for sale
    public Car(int year, String make, String model, Category category, boolean forSale) {
        this.id = 0;
        this.year = year;
        this.make = make;
        this.model = model;
        this.category = category;
        this.forSale = forSale;
    }

    public int getID() {
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
    public void setID(int id) {
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
