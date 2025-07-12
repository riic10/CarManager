package ui;

import model.*;

import java.util.Scanner;

// Car collection manager app
public class CarCollectionManager { 

    private Collection collection;
    private Scanner input;

    public CarCollectionManager() {
        runManager();    
    }

    public void runManager() {
        System.out.println("test");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: initializes collections
    private void init() {
        // STUB
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        // STUB
    }

    // EFFECTS: returns the entire collection
    private Collection doGetCollection() {
        return null; // STUB
    }

    // MODIFIES: this
    // EFFECTS: Adds a new car to the collection
    private void doAddCar() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: Removes a car from the collection
    private void doRemoveCar() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: Sets a car in the collection as for sale
    private void doSetForSale() {
        // STUB
    }

    // MODIFIES: this
    // EFFECTS: Sets a car in the collection as not for sale
    private void doSetNotForSale() {
        // STUB
    }

    // EFFECTS: Returns only the cars that are for sale in the collection
    private Collection doFilterForSale() {
        return null; // STUB
    }

    // EFFECTS: Returns only the cars in the collection which match a given category
    private Collection doFilterByCollection() {
        return null; // STUB
    }
}
