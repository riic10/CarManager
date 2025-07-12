package model;

// Creates a new Car object with fields representing the production year, make, model,
// category, and whether or not it's for sale or not.
public class Car {
    private int year;
    private String make;
    private String model;
    private Category category;
    private boolean forSale;

    // EFFECTS: Constructs a Car with the given year, make, model, category,
    // and set to not for sale
    public Car(int year, String make, String model, Category category, boolean forSale) {
        this.year = year;
        this.make = make;
        this.model = model;
        this.category = category;
        this.forSale = false;
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
    // EFFECTS: Sets the Car as being for sale
    public void setForSale() {
        this.forSale = true;
    }

    // MODIFIES: this
    // EFFECTS: Sets the Car as being not for sale
    public void setNotForSale() {
        this.forSale = false;
    }
}
