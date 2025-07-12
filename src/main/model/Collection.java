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
        this.collection.add(c);
    }

    // MODIFIES: this
    // EFFECTS: Removes the given Car from the Collection
    public void removeCar(Car c) {
        this.collection.remove(c);
    }

    // EFFECTS: Returns the entire Collection
    public ArrayList<Car> getCollection() {
        return this.collection;
    }

    // EFFECTS: Returns a list of Cars in the Collection filtered by 
    // the given category 
    public ArrayList<Car> filterCollection(Category category) {
        ArrayList<Car> matches = new ArrayList<Car>();
        for (Car c : collection) {
            if (c.getCategory() == category) {
                matches.add(c);
            }
        }
        return matches;
    }
}
