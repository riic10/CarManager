package persistence;

import model.Category;
import model.Car;
import model.Collection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Collection c = new Collection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCollection() {
        try {
            Collection c = new Collection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCollection.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCollection.json");
            c = reader.read();
            assertEquals(0, c.getCollection().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneCar() {
        try {
            Collection c = new Collection();
            c.addCar(new Car(2018, "lexus", "rx350", Category.LUXURY, false));
            JsonWriter writer = new JsonWriter("./data/testWriterOneCar.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneCar.json");
            c = reader.read();
            ArrayList<Car> cars = c.getCollection();
            assertEquals(1, cars.size());
            checkCar(2018, "lexus", "rx350", Category.LUXURY, false, cars.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    void testWriterTwoCars() {
        try {
            Collection c = new Collection();
            c.addCar(new Car(2018, "lexus", "rx350", Category.LUXURY, false));
            c.addCar(new Car(2010, "bugatti", "veyron", Category.SUPERCAR, true));
            JsonWriter writer = new JsonWriter("./data/testWriterTwoCars.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTwoCars.json");
            c = reader.read();
            ArrayList<Car> cars = c.getCollection();
            assertEquals(2, cars.size());
            checkCar(2018, "lexus", "rx350", Category.LUXURY, false, cars.get(0));
            checkCar(2010, "bugatti", "veyron", Category.SUPERCAR, true, cars.get(1));
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
}