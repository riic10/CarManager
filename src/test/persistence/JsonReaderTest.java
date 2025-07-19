package persistence;

import model.Category;
import model.Car;
import model.Collection;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Collection c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCollection.json");
        try {
            Collection c = reader.read();
            assertEquals(0, c.getCollection().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderMixedCars.json");
        try {
            Collection c = reader.read();
            List<Car> collection = c.getCollection();
            assertEquals(2, collection.size());
            checkCar(2020, "toyota", "corolla", Category.ECONOMY, false, c.getCollection().get(0));
            checkCar(1969, "dodge", "charger", Category.MUSCLE, false, c.getCollection().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
