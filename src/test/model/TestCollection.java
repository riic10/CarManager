package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCollection {
    
    Collection testCollection;
    Car testCar1;
    Car testCar2;
    Car testCar3;
    Car testCar4;
    Car testCar5;

    @BeforeEach
    void runBefore() {
        testCollection = new Collection();
        testCar1 = new Car(2000, "Honda", "Civic", Category.ECONOMY, false);
        testCar2 = new Car(2025, "Porsche", "911 GT3 RS", Category.SUPERCAR, false);
        testCar3 = new Car(1969, "Dodge", "Charger", Category.MUSCLE, false);
        testCar4 = new Car(2020, "Mercedes", "W11", Category.RACECAR, false);
        testCar5 = new Car(2017, "Bugatti", "Chiron", Category.SUPERCAR, false);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCollection.getCollection().size());
    }

    @Test
    void testAddOneCar() {
        testCollection.addCar(testCar1);
        assertEquals(1, testCollection.getCollection().size());
    }

    @Test
    void testAddMultipleCars() {
        testCollection.addCar(testCar1);
        testCollection.addCar(testCar2);
        assertEquals(2, testCollection.getCollection().size());
        testCollection.addCar(testCar3);
        assertEquals(3, testCollection.getCollection().size());
    }

    @Test
    void testRemoveOneCar() {
        testCollection.addCar(testCar1);
        assertEquals(testCar1, testCollection.getCollection().get(0));
        testCollection.removeCar(testCar1);
        assertEquals(0, testCollection.getCollection().size());
    }

    @Test
    void testRemoveMultipleCars() {
        testCollection.addCar(testCar3);
        testCollection.addCar(testCar2);
        assertEquals(testCar3, testCollection.getCollection().get(0));
        assertEquals(testCar2, testCollection.getCollection().get(1));
        testCollection.removeCar(testCar2);
        assertFalse(testCollection.getCollection().contains(testCar2));
    }

    @Test
    void testFilterCollectionOneCar() {
        testCollection.addCar(testCar1);
        assertTrue(testCollection.filterCollection(Category.ECONOMY).contains(testCar1));
    }

    @Test 
    void testFilterCollectionMultipleCars() {
        testCollection.addCar(testCar1);
        testCollection.addCar(testCar2);
        testCollection.addCar(testCar3);
        testCollection.addCar(testCar4);
        testCollection.addCar(testCar5);
        assertTrue(testCollection.filterCollection(Category.SUPERCAR).contains(testCar2));
        assertTrue(testCollection.filterCollection(Category.SUPERCAR).contains(testCar5));
        assertFalse(testCollection.filterCollection(Category.SUPERCAR).contains(testCar1));
        assertFalse(testCollection.filterCollection(Category.SUPERCAR).contains(testCar3));
        assertFalse(testCollection.filterCollection(Category.SUPERCAR).contains(testCar4));
    }
}
