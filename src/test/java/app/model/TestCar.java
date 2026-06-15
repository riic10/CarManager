package app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCar {

    Car testCar1;
    Car testCar2;

    @BeforeEach
    void runBefore() {
        testCar1 = new Car(2000, "Honda", "Civic", Category.ECONOMY, false);
        testCar2 = new Car(2025, "Porsche", "911 GT3 RS", Category.SUPERCAR, false);
    }

    @Test
    void testConstructor() {
        assertNull(testCar1.getID());
        assertEquals(2000, testCar1.getYear());
        assertEquals("Honda", testCar1.getMake());
        assertEquals("Civic", testCar1.getModel());
        assertEquals(Category.ECONOMY, testCar1.getCategory());
        assertFalse(testCar1.getForSale());
    }

    @Test
    void testForNextID() {
        assertNull(testCar1.getID());
        assertNull(testCar2.getID());
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
        assertEquals("ID: null -- 2000 Honda Civic -- Category: ECONOMY -- For sale?: false", testCar1.toString());
    }
}
