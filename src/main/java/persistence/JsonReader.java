package persistence;

import model.Category;
import model.Car;
import model.Collection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a reader that reads collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        Collection c = new Collection();
        addCars(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses cars from JSON object and adds them to collection
    private void addCars(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("collection");
        for (Object json : jsonArray) {
            JSONObject nextCar = (JSONObject) json;
            addCar(c, nextCar);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses car from JSON object and adds it to collection
    private void addCar(Collection c, JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        int year = jsonObject.getInt("year");
        String make = jsonObject.getString("make");
        String model = jsonObject.getString("model");
        Category category = Category.valueOf(jsonObject.getString("category"));
        boolean forSale = jsonObject.getBoolean("forSale");
        Car car = new Car(year, make, model, category, forSale);
        car.setID(id);
        c.loadCar(car);
    }
}
