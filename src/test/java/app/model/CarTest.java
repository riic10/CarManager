package app.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarTest {

    Car testCar1;

    @BeforeEach
    void runBefore() {
        testCar1 = new Car(2000, "Honda", "Civic", Category.ECONOMY, false);
    }

    @Test
    void testConstructor() {
        assertNull(testCar1.getId());
        assertEquals(2000, testCar1.getYear());
        assertEquals("Honda", testCar1.getMake());
        assertEquals("Civic", testCar1.getModel());
        assertEquals(Category.ECONOMY, testCar1.getCategory());
        assertFalse(testCar1.isForSale());
    }

    @Test
    void testToString() {
        assertEquals("ID: null -- 2000 Honda Civic -- Category: ECONOMY -- For sale?: false", testCar1.toString());
    }
}
