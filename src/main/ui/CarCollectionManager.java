package ui;

import model.*;

import java.util.ArrayList;
import java.util.Scanner;

// Car collection manager app
public class CarCollectionManager { 

    private Collection collection;
    private Scanner input;

    public CarCollectionManager() {
        runManager();    
    }

    public void runManager() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
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
    }

    // EFFECTS: returns the entire collection
    private void doGetCollection() {
        collection.displayCollection();
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
        collection.displayCars(filteredList);
    }

    // EFFECTS: Returns only the cars in the collection which match a given category
    private void doFilterByCategory() {
        System.out.print("Enter the category of cars to see: ");
        Category category = parseCategory(input.nextLine());
        ArrayList<Car> filteredList = this.collection.filterCollectionByCategory(category);
        collection.displayCars(filteredList);
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
}
