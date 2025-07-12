package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCar {

    Car testCar;

    @BeforeEach
    void runBefore() {
        testCar = new Car(2000, "Honda", "Civic", Category.ECONOMY, false);
    }

    @Test
    void testConstructor() {
        assertEquals(2000, testCar.getYear());
        assertEquals("Honda", testCar.getMake());
        assertEquals("Civic", testCar.getModel());
        assertEquals(Category.ECONOMY, testCar.getCategory());
        assertFalse(testCar.getForSale());
    }

    @Test
    void testSetForSale() {
        assertFalse(testCar.getForSale());
        testCar.setForSale();
        assertTrue(testCar.getForSale());
    }

    @Test
    void testSetNotForSale() {
        assertFalse(testCar.getForSale());
        testCar.setForSale();
        assertTrue(testCar.getForSale());
        testCar.setNotForSale();
        assertFalse(testCar.getForSale());
    }
}
