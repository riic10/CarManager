package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Car collection manager app
public class CarCollectionManager { 

    private Collection collection;
    private Scanner input;
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public CarCollectionManager() {
        runManager();    
    }

    // Adapted from the TellerApp example
    public void runManager() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\n--Application Closed--");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doGetCollection();
        } else if (command.equals("a")) {
            doAddCar();
        } else if (command.equals("r")) {
            doRemoveCar();
        } else if (command.equals("s")) {
            doSetForSale();
        } else if (command.equals("n")) {
            doSetNotForSale();
        } else if (command.equals("fs")) {
            doFilterForSale();
        } else if (command.equals("fc")) {
            doFilterByCategory();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes collections
    private void init() {
        collection = new Collection();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> See entire collection");
        System.out.println("\ta -> Add a new car");
        System.out.println("\tr -> Remove a car");
        System.out.println("\ts -> Set a car for sale");
        System.out.println("\tn -> Set a car as not for sale");
        System.out.println("\tfs -> See cars that are for sale");
        System.out.println("\tfc -> See cars in a category");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: returns the entire collection
    private void doGetCollection() {
        displayCollection();
    }

    // MODIFIES: this
    // EFFECTS: Adds a new car to the collection
    private void doAddCar() {
        System.out.print("Enter the car's production year: ");
        int year = input.nextInt();
        input.nextLine();
        System.out.print("Enter the car's make: ");
        String make = input.nextLine();
        System.out.print("Enter the car's model: ");
        String model = input.nextLine();
        System.out.print("Enter the car's category: ");
        Category category = parseCategory(input.nextLine());

        Car car = new Car(year, make, model, category, false);
        this.collection.addCar(car);
        int id = car.getID();
        String newid = String.valueOf(id);

        System.out.println("The " + make + " " + model + " has been added to the collection with ID=" + newid);
    }

    // MODIFIES: this
    // EFFECTS: Removes a car from the collection
    private void doRemoveCar() {
        System.out.print("Enter the ID for the car to be removed: ");
        int remove = input.nextInt();
        collection.removeCar(remove);
        System.out.println("--Car has been removed--");
    }

    // MODIFIES: this
    // EFFECTS: Sets a car in the collection as for sale
    private void doSetForSale() {
        System.out.print("Enter the ID for the car to set for sale: ");
        int toBeListed = input.nextInt();
        collection.getCar(toBeListed).setForSale();
        System.out.println("--Car has been set for sale--");
    }

    // MODIFIES: this
    // EFFECTS: Sets a car in the collection as not for sale
    private void doSetNotForSale() {
        System.out.print("Enter the ID for the car to set not for sale: ");
        int toBeDelisted = input.nextInt();
        collection.getCar(toBeDelisted).setNotForSale();
        System.out.println("--Car has been set to not for sale--");
    }

    // EFFECTS: Returns only the cars that are for sale in the collection
    private void doFilterForSale() {
        ArrayList<Car> filteredList = this.collection.filterCollectionForSale();
        displayCars(filteredList);
    }

    // EFFECTS: Returns only the cars in the collection which match a given category
    private void doFilterByCategory() {
        System.out.print("Enter the category of cars to see: ");
        String userCategory = input.nextLine();
        Category category = parseCategory(userCategory);
        ArrayList<Car> filteredList = this.collection.filterCollectionByCategory(category);
        displayCars(filteredList);
    }

    // REQUIRES: Input string must be one of: RACECAR, SUPERCAR, SPORTSCAR, LUXURY, MUSCLE,
    //           VINTAGE, ECONOMY, OTHER (case sensitive)
    // MODIFIES: s
    // EFFECTS: Converts the given string into it's corresponding Category
    private Category parseCategory(String s) {
        switch (s) {
            case "RACECAR":
                return Category.RACECAR;
            case "SUPERCAR":
                return Category.SUPERCAR;
            case "SPORTSCAR":
                return Category.SPORTSCAR;
            case "LUXURY":
                return Category.LUXURY;
            case "MUSCLE":
                return Category.MUSCLE;
            case "VINTAGE":
                return Category.VINTAGE;
            case "ECONOMY":
                return Category.ECONOMY;
            default:
                return Category.OTHER;
        }
    }

    // EFFECTS: Takes an ArrayList<Car> and displays all of them in the console.
    // This method is meant to be used with filtered lists and not the entire collection.
    private void displayCars(ArrayList<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("--No cars--");
        }
        for (Car c : carList) {
            System.out.println(c.toString());
        }
    }

    // EFFECTS: Displays the entire collection in the console
    private void displayCollection() {
        if (collection.getCollection().size() == 0) {
            System.out.println("--No cars--");
        }
        for (Car c : collection.getCollection()) {
            System.out.println(c.toString());
        }
    }
    
    // EFFECTS: saves the collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
            System.out.println("The collection has been saved to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save the collection to: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads collection from file
    private void loadWorkRoom() {
        try {
            collection = jsonReader.read();
            System.out.println("Loaded the collection from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load the collection from: " + JSON_STORE);
        }
    }
}
