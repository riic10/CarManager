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
    public void removeCar(int idToBeRemoved) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getID() == idToBeRemoved) {
                collection.remove(i);
                break;
            }
        }
    }

    // EFFECTS: Returns the car in the collection with a given ID number
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

    // EFFECTS: Takes an ArrayList<Car> and displays all of them in the console.
    // This method is meant to be used with filtered lists and not the entire collection.
    public void displayCars(ArrayList<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("--No cars--");
        }
        for (Car c : carList) {
            System.out.println(c.toString());
        }
    }

    // EFFECTS: Displays the entire collection in the console
    public void displayCollection() {
        if (collection.isEmpty()) {
            System.out.println("--No cars--");
        }
        for (Car c : collection) {
            System.out.println(c.toString());
        }
    }    
}
