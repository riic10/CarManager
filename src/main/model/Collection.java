package model;

import java.util.ArrayList;

// A list of Cars in a collector's collection
public class Collection {
    private ArrayList<Car> collection;

    // EFFECTS: Constructs a new empty Collection of Cars
    public Collection() {
        collection = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Adds the given Car to the Collection 
    public void addCar(Car c) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: Removes the given Car from the Collection
    public void removeCar(Car c) {
        // STUB
    }

    // EFFECTS: Returns the entire Collection
    public ArrayList<Car> getCollection() {
        return null; // STUB
    }

    // EFFECTS: Returns a list of Cars in the Collection filtered by 
    // the given category 
    public ArrayList<Car> getFilteredCollection(Category category) {
        return null; // STUB
    }
}
