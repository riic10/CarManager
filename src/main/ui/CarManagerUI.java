package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;


public class CarManagerUI {
    private JFrame frame;
    private JPanel panel;

    private Collection collection;
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // initializes and sets up the gui
    public CarManagerUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        
        openingMessage();

        frame = new JFrame();
        panel = new JPanel();
        createToolBar();
        frame.setSize(700, 500);
        panel.setBackground(Color.decode("#C9A0DC"));
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });
    }

    // EFFECTS: Draws the menu buttons which can be clicked
    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
    
        JButton addCarButton = new JButton("Add Car");
        JButton removeCarButton = new JButton("Remove Car");
        JButton filterForSaleButton = new JButton("Show cars for sale");
        toolBar.add(addCarButton);
        toolBar.add(removeCarButton);
        toolBar.add(filterForSaleButton);

        addCarButton.addActionListener(e -> handleAddCar());
        removeCarButton.addActionListener(e -> handleRemoveCar());
        filterForSaleButton.addActionListener(e -> handleFilterForSale());

        frame.add(toolBar, BorderLayout.NORTH);
    }

    // EFFECTS: Prompts the user to select if they would like to load
    // their collection from file 
    private void openingMessage() {
        int choice = JOptionPane.showConfirmDialog(
                null, 
                "Would you like to load existing car data?", 
                null, 
                JOptionPane.YES_NO_OPTION
        );
    
        if (choice == JOptionPane.YES_OPTION) {
            // Open the program with data loaded from file
            loadCollection();
        } else {
            // Open the program without loading from file
            collection = new Collection();
        }
    }

    // EFFECTS: Loads the collection from file
    private void loadCollection() {
        try {
            collection = jsonReader.read();
            JOptionPane.showMessageDialog(frame, "Data loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    frame, 
                    "Could not load data from file. Starting with empty collection.", 
                    "Load Error",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // EFFECTS: Adds a new car to the collection
    private void handleAddCar() {
        JOptionPane.showMessageDialog(frame, "Add Car clicked!");
    }

    // REQUIRES: collection contains Car
    // EFFECTS: Removes an existing car from the collection
    private void handleRemoveCar() {
        JOptionPane.showMessageDialog(frame, "Remove Car clicked!");
    }

    // EFFECTS: Returns only the cars matching the selected category
    private void handleFilterForSale() {
        JOptionPane.showMessageDialog(frame, "Filter for sale clicked!");
    }

    // EFFECTS: Saves the collection to file
    private void saveCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(collection);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                    frame, 
                    "Could not save data to file.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // EFFECTS: Prompts the user to select if they want to save their
    // progress to file before closing the program
    private void exitProgram() {
        int choice = JOptionPane.showConfirmDialog(
                frame,
                "Do you want to save your progress before exiting?",
                "Save Before Exit",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            saveCollection();
            System.exit(0);
        } else if (choice == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }
}