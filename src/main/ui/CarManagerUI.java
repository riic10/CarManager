package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class CarManagerUI {
    private JFrame frame;
    private JPanel panel;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private static final String[] COLUMN_NAMES = { "ID", "Year", "Make", "Model", "Category", "For Sale" };

    private Collection collection;
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private DialogManager dialogManager;
    private TableManager tableManager;
    private FileManager fileManager;

    // initializes and sets up the gui
    public CarManagerUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        fileManager = new FileManager(jsonWriter, jsonReader);

        openingMessage();
        initializeTable();

        tableManager = new TableManager(tableModel, COLUMN_NAMES);
        dialogManager = new DialogManager(frame, collection, tableManager);

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
    // current collection
    private void initializeTable() {
        tableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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

    // EFFECTS: Refreshes the table with current collection data
    private void refreshTable() {
        tableManager.refreshTable(collection);
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
    // handleFilterForSale()
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
                JOptionPane.YES_NO_OPTION);

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
        collection = fileManager.loadCollection(frame);
        if (collection == null) {
            collection = new Collection();
        }
    }

    // REQUIRES: All fields must be filled out
    // MODIFIES: this
    // EFFECTS: Adds a new car to the collection
    private void handleAddCar() {
        dialogManager.showAddCarForm();
        refreshTable();
    }

    // REQUIRES: collection contains Car
    // MODIFIES: this
    // EFFECTS: Removes an existing car from the collection
    private void handleRemoveCar() {
        dialogManager.showRemovePrompt();
        refreshTable();
    }

    // EFFECTS: Returns only the cars matching the selected category
    private void handleFilterForSale() {
        ArrayList<Car> filtered = collection.filterCollectionForSale();
        dialogManager.showForSale(filtered, "For Sale");
    }

    // EFFECTS: Saves the collection to file
    private void saveCollection() {
        fileManager.saveCollection(frame, collection);
    }

    // EFFECTS: Prints all logged events to the console
    private void printLog() {
        EventLog eventLog = EventLog.getInstance();
        for (Event event : eventLog) {
            System.out.println(event.toString());
            System.out.println();
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
                JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            saveCollection();
            printLog();
            System.exit(0);
        } else if (choice == JOptionPane.NO_OPTION) {
            printLog();
            System.exit(0);
        }
    }
}