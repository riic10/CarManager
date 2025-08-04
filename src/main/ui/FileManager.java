package ui;

import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Manages loading and saving from file for the program
public class FileManager {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public FileManager(JsonWriter jsonWriter, JsonReader jsonReader) {
        this.jsonWriter = jsonWriter;
        this.jsonReader = jsonReader;
    }

    // EFFECTS: Loads the collection from file
    public Collection loadCollection(JFrame parentFrame) {
        try {
            Collection collection = jsonReader.read();
            JOptionPane.showMessageDialog(parentFrame, "Data loaded successfully!");
            return collection;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    parentFrame, 
                    "Could not load data from file. Starting with empty collection.", 
                    "Load Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }
    }

    // EFFECTS: Saves the collection to file
    public void saveCollection(JFrame parentFrame, Collection collection) {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    parentFrame, 
                    "Could not save data to file.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}