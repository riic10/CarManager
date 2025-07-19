package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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

    // REQUIRES: idToBeRemoved >= 1
    // MODIFIES: this
    // EFFECTS: Removes the given Car from the Collection
    public void removeCar(int idToBeRemoved) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getID() == idToBeRemoved) {
                collection.remove(i);
                break;
            }
        }
    }

    // REQUIRES: carID >= 1
    // EFFECTS: Returns the car in the collection with a given ID number
    //          or null if not found
    public Car getCar(int carID) {
        for (Car c : collection) {
            if (c.getID() == carID) {
                return c;
            }
        }
        return null; 
    }

    // EFFECTS: Returns the entire Collection
    public ArrayList<Car> getCollection() {
        return this.collection;
    }

    // REQUIRES: category must be of type Category
    // EFFECTS: Returns a list of Cars in the Collection filtered by 
    // the given category 
    public ArrayList<Car> filterCollectionByCategory(Category category) {
        ArrayList<Car> matches = new ArrayList<Car>();
        for (Car c : collection) {
            if (c.getCategory() == category) {
                matches.add(c);
            }
        }
        return matches;
    }

    // EFFECTS: Returns a list of Cars in the Collection which are 
    // listed as for sale
    public ArrayList<Car> filterCollectionForSale() {
        ArrayList<Car> matches = new ArrayList<Car>();
        for (Car c : collection) {
            if (c.getForSale()) {
                matches.add(c);
            }
        }
        return matches;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("collection", carsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray carsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Car c : collection) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
