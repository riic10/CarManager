package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCar {

    Car testCar1;
    Car testCar2;

    @BeforeEach
    void runBefore() {
        testCar1 = new Car(2000, "Honda", "Civic", Category.ECONOMY, false);
        testCar2 = new Car(2025, "Porsche", "911 GT3 RS", Category.SUPERCAR, false);
        testCar1.resetID();
        testCar2.resetID();
    }

    @Test
    void testConstructor() {
        assertEquals(1, testCar1.getID());
        assertEquals(2000, testCar1.getYear());
        assertEquals("Honda", testCar1.getMake());
        assertEquals("Civic", testCar1.getModel());
        assertEquals(Category.ECONOMY, testCar1.getCategory());
        assertFalse(testCar1.getForSale());
    }

    @Test
    void testForNextID() {
        assertEquals(1, testCar1.getID());
        assertEquals(2, testCar2.getID());
    }

    @Test
    void testSetForSale() {
        assertFalse(testCar1.getForSale());
        testCar1.setForSale();
        assertTrue(testCar1.getForSale());
    }

    @Test
    void testSetNotForSale() {
        assertFalse(testCar1.getForSale());
        testCar1.setForSale();
        assertTrue(testCar1.getForSale());
        testCar1.setNotForSale();
        assertFalse(testCar1.getForSale());
    }

    @Test
    void testToString() {
        assertEquals("ID: 1 -- 2000 Honda Civic -- Category: ECONOMY -- For sale?: false", testCar1.toString());
    }

    @Test
    void testToJson() {
        JSONObject expectedObject = new JSONObject();
        expectedObject.put("year", 2000);
        expectedObject.put("make", "Honda");
        expectedObject.put("model", "Civic");
        expectedObject.put("category", Category.ECONOMY);
        expectedObject.put("forSale", false);

        JSONObject actualObject = testCar1.toJson();

        assertTrue(expectedObject.similar(actualObject));
    }
}
