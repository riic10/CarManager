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
        toolBar.add(createAddCarButton());
        toolBar.add(createRemoveCarButton());
        toolBar.add(createFilterForSaleButton());

        frame.add(toolBar, BorderLayout.NORTH);
    }

    // EFFECTS: Creates the add car button and connects it to
    // handleAddCar()
    private JButton createAddCarButton() {
        JButton addCarButton = new JButton("Add Car");
        addCarButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddCar();
            }
        });
        addCarButton.setFocusable(false);
        return addCarButton;
    }

    // EFFECTS: Creates the remove car button and connects it to
    // handleRemoveCar()
    private JButton createRemoveCarButton() {
        JButton removeCarButton = new JButton("Remove Car");
        removeCarButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRemoveCar();
            }
        });
        removeCarButton.setFocusable(false);
        return removeCarButton;
    }

    // EFFECTS: Creates the add car button and connects it to
    // handleAddCar()
    private JButton createFilterForSaleButton() {
        JButton filterForSaleButton = new JButton("Show for sale");
        filterForSaleButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleFilterForSale();
            }
        });
        filterForSaleButton.setFocusable(false);
        return filterForSaleButton;
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
        showAddCarDialog();
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

    private void showAddCarDialog() {
        JDialog dialog = new JDialog(frame, "Add New Car", true);
        dialog.setLayout(new GridBagLayout());
        
        JTextField yearField = new JTextField(10);
        JTextField makeField = new JTextField(15);
        JTextField modelField = new JTextField(15);
        String[] categories = {"RACECAR", "SUPERCAR", "SPORTSCAR", "LUXURY", "MUSCLE", "VINTAGE", "ECONOMY", "OTHER"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        JCheckBox forSaleCheckbox = new JCheckBox("For Sale");

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
    
    private Car processDialogInput(JDialog dialog, JTextField yearField, JTextField makeField,
                                 JTextField modelField, JComboBox<String> categoryCombo, JCheckBox forSaleCheckbox) {
        int year = Integer.parseInt(yearField.getText().trim());
        String make = makeField.getText().trim();
        String model = modelField.getText().trim();
        Category category = parseCategory(categoryCombo.getSelectedItem().toString());
        boolean forSale = forSaleCheckbox.isSelected();
        
        Car car = new Car(year, make, model, category, forSale);
        return car;
    }

    // REQUIRES: Input string must be one of: RACECAR, SUPERCAR, SPORTSCAR, LUXURY, MUSCLE,
    //           VINTAGE, ECONOMY, OTHER (case sensitive)
    // MODIFIES: s
    // EFFECTS: Converts the given string into it's corresponding Category
    private Category parseCategory(String s) {
        switch (s) {
            case "RACECAR":
                return Category.RACECAR;
            case "SUPERCAR":
                return Category.SUPERCAR;
            case "SPORTSCAR":
                return Category.SPORTSCAR;
            case "LUXURY":
                return Category.LUXURY;
            case "MUSCLE":
                return Category.MUSCLE;
            case "VINTAGE":
                return Category.VINTAGE;
            case "ECONOMY":
                return Category.ECONOMY;
            default:
                return Category.OTHER;
        }
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