package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class CarManagerUI {
    private JFrame frame;
    private JPanel panel;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private static final String[] COLUMN_NAMES = {"ID", "Year", "Make", "Model", "Category", "For Sale"};

    private Collection collection;
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // initializes and sets up the gui
    public CarManagerUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        
        openingMessage();
        initializeTable();

        frame = new JFrame();
        panel = new JPanel();
        createToolBar();
        setupTablePanel();

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
        refreshTable();
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
    
    // EFFECTS: Initializes the main table for the user to see the
    //          current collection
    private void initializeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // false means the user cannot edits rows
            }
        };
        carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.getTableHeader().setReorderingAllowed(false);
    }

    // EFFECTS: Sets up the panel with the table
    private void setupTablePanel() {
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.decode("#C9A0DC"));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(carTable);
        
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: Adds each car in the collection as a row in the table
    private void fillTable() {
        ArrayList<Car> cars = collection.getCollection();
        for (Car car : cars) {
            Object[] rowData = {
                car.getID(),
                car.getYear(),
                car.getMake(),
                car.getModel(),
                car.getCategory().toString(),
                car.getForSale() ? "Yes" : "No"
            };
            tableModel.addRow(rowData);
        }
    }

    private void fillTable(DefaultTableModel model, ArrayList<Car> cars) {
        for (Car car : cars) {
            Object[] rowData = {
                car.getID(),
                car.getYear(),
                car.getMake(),
                car.getModel(),
                car.getCategory().toString(),
                car.getForSale() ? "Yes" : "No"
            };
            model.addRow(rowData);
        }
    }

    // EFFECTS: Refreshes the table with current collection data
    private void refreshTable() {
        tableModel.setRowCount(0);
        
        if (collection != null) {
            fillTable();
        }
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
        showAddCarForm();
        refreshTable();
    }

    // REQUIRES: collection contains Car
    // EFFECTS: Removes an existing car from the collection
    private void handleRemoveCar() {
        showRemovePrompt();
        refreshTable();
    }

    // EFFECTS: Returns only the cars matching the selected category
    private void handleFilterForSale() {
        ArrayList<Car> filtered = collection.filterCollectionForSale();
        showForSale(filtered, "For Sale");
    }

    // EFFECTS: Shows the form that the user fills out when adding a new car and
    // places it into the collection
    private void showAddCarForm() {
        JDialog dialog = new JDialog(frame, "Add New Car", true);
        
        JTextField yearField = new JTextField(10);
        JTextField makeField = new JTextField(15);
        JTextField modelField = new JTextField(15);
        String[] categories = {"RACECAR", "SUPERCAR", "SPORTSCAR", "LUXURY", "MUSCLE", "VINTAGE", "ECONOMY", "OTHER"};
        JComboBox<String> category = new JComboBox<>(categories);
        JCheckBox forSaleCheckbox = new JCheckBox("For Sale");

        addCarFormComponents(dialog, yearField, makeField, modelField, category, forSaleCheckbox);
        addFormButtons(dialog, yearField, makeField, modelField, category, forSaleCheckbox);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    // EFFECTS: Draws labels for each field in the form for adding a new car
    private void addCarFormComponents(JDialog dialog, JTextField yearField, JTextField makeField, 
                                   JTextField modelField, JComboBox<String> category, JCheckBox forSaleCheckbox) {
        dialog.setLayout(new GridLayout(6, 2, 5, 5));
        
        dialog.add(new JLabel("Year:"));
        dialog.add(yearField);
        dialog.add(new JLabel("Make:"));
        dialog.add(makeField);
        dialog.add(new JLabel("Model:"));
        dialog.add(modelField);
        dialog.add(new JLabel("Category:"));
        dialog.add(category);
        dialog.add(new JLabel("For Sale:"));
        dialog.add(forSaleCheckbox);
    }

    // EFFECTS: Draws the add car and cancel buttons in the add car form
    private void addFormButtons(JDialog dialog, JTextField yearField, JTextField makeField,
                                JTextField modelField, JComboBox<String> category, JCheckBox forSaleCheckbox) {
        JButton okButton = new JButton("Add Car");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Car car = createCar(yearField, makeField, modelField, category, forSaleCheckbox);
                collection.addCar(car);
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(okButton);
        dialog.add(cancelButton);
    }
    
    // EFFECTS: Creates a new car based on the user's input
    private Car createCar(JTextField yearField, JTextField makeField,
                                 JTextField modelField, JComboBox<String> categoryCombo, JCheckBox forSaleCheckbox) {
        int year = Integer.parseInt(yearField.getText().trim());
        String make = makeField.getText().trim();
        String model = modelField.getText().trim();
        Category category = parseCategory(categoryCombo.getSelectedItem().toString());
        boolean forSale = forSaleCheckbox.isSelected();
        
        Car car = new Car(year, make, model, category, forSale);
        return car;
    }

    // EFFECTS: Draws the prompt which the user will interact with when they would
    //          like to remove a car from the collection
    private void showRemovePrompt() {
        JDialog dialog = new JDialog(frame, "Remove a Car", true);
        
        JTextField idToRemove = new JTextField(10);

        removeCarFormComponents(dialog, idToRemove);
        removeCarButton(dialog, idToRemove);

        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    // EFFECTS: Sets the layout of the user prompt and labels the field which
    //          the user will enter their choice
    private void removeCarFormComponents(JDialog dialog, JTextField idToBeRemoved) {
        dialog.setLayout(new GridLayout(2, 2, 5, 5));
        dialog.add(new JLabel("ID of car to remove:"));
        dialog.add(idToBeRemoved);
    }

    // EFFECTS: Draws the buttons which are a part of the prompt for the
    //          user to choose which car to remove from the collection
    private void removeCarButton(JDialog dialog, JTextField idToBeRemoved) {
        JButton okButton = new JButton("Remove Car");
        JButton cancelButton = new JButton("Cancel");
        
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCar(idToBeRemoved);
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(okButton);
        dialog.add(cancelButton);
    }

    // REQUIRES: idToBeRemoved must be in the collection
    // EFFECTS: Removes the car in the collection which matches the
    //          idToBeRemoved
    private void removeCar(JTextField idToBeRemoved) {
        int id = Integer.parseInt(idToBeRemoved.getText().trim());
        collection.removeCar(id);
    }

    // EFFECTS: Shows filtered results in a popup dialog
    private void showForSale(ArrayList<Car> cars, String title) {
        JDialog dialog = new JDialog(frame, title, true);
        
        DefaultTableModel filteredModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable filteredTable = new JTable(filteredModel);

        fillTable(filteredModel, cars);

        JScrollPane scrollPane = new JScrollPane(filteredTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        
        dialog.add(scrollPane);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
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