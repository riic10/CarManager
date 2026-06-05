package ui;

import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

// Manages all the dialogs, popups, and user prompts that are shown in the GUI
public class DialogManager {
    private JFrame parentFrame;
    private Collection collection;
    private TableManager tableManager;
    private static final String[] COLUMN_NAMES = { "ID", "Year", "Make", "Model", "Category", "For Sale" };

    public DialogManager(JFrame parentFrame, Collection collection, TableManager tableManager) {
        this.parentFrame = parentFrame;
        this.collection = collection;
        this.tableManager = tableManager;
    }

    // EFFECTS: Shows the form that the user fills out when adding a new car and
    // places it into the collection
    public void showAddCarForm() {
        JDialog dialog = new JDialog(parentFrame, "Add New Car", true);

        JTextField yearField = new JTextField(10);
        JTextField makeField = new JTextField(15);
        JTextField modelField = new JTextField(15);
        String[] categories = { "RACECAR", "SUPERCAR", "SPORTSCAR", "LUXURY", "MUSCLE", "VINTAGE", "ECONOMY", "OTHER" };
        JComboBox<String> category = new JComboBox<>(categories);
        JCheckBox forSaleCheckbox = new JCheckBox("For Sale");

        addCarFormComponents(dialog, yearField, makeField, modelField, category, forSaleCheckbox);
        addCarFormButtons(dialog, yearField, makeField, modelField, category, forSaleCheckbox);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
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
    private void addCarFormButtons(JDialog dialog, JTextField yearField, JTextField makeField,
            JTextField modelField, JComboBox<String> category, JCheckBox forSaleCheckbox) {
        JButton okButton = new JButton("Add Car");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Car car = createCar(yearField, makeField, modelField, category, forSaleCheckbox);
                collection.addCar(car);
                dialog.dispose();
                showSuccessDialog("Car added successfully!", "./image/thumbs_up.png");
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
    // like to remove a car from the collection
    public void showRemovePrompt() {
        JDialog dialog = new JDialog(parentFrame, "Remove a Car", true);

        JTextField idToRemove = new JTextField(10);

        removeCarFormComponents(dialog, idToRemove);
        removeCarButton(dialog, idToRemove);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    // EFFECTS: Sets the layout of the user prompt and labels the field which
    // the user will enter their choice
    private void removeCarFormComponents(JDialog dialog, JTextField idToBeRemoved) {
        dialog.setLayout(new GridLayout(2, 2, 5, 5));
        dialog.add(new JLabel("ID of car to remove:"));
        dialog.add(idToBeRemoved);
    }

    // EFFECTS: Draws the buttons which are a part of the prompt for the
    // user to choose which car to remove from the collection
    private void removeCarButton(JDialog dialog, JTextField idToBeRemoved) {
        JButton okButton = new JButton("Remove Car");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCar(idToBeRemoved);
                dialog.dispose();
                showSuccessDialog("Car removed successfully!", "./image/thumbs_up.png");
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
    // idToBeRemoved
    private void removeCar(JTextField idToBeRemoved) {
        int id = Integer.parseInt(idToBeRemoved.getText().trim());
        collection.removeCar(id);
    }

    // EFFECTS: Shows filtered results in a popup dialog
    public void showForSale(ArrayList<Car> cars, String title) {
        JDialog dialog = new JDialog(parentFrame, title, true);

        DefaultTableModel filteredModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable filteredTable = new JTable(filteredModel);

        tableManager.fillTable(filteredModel, cars);

        JScrollPane scrollPane = new JScrollPane(filteredTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        dialog.add(scrollPane);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    // EFFECTS: Shows a success confirmation with the thumbs up image
    public void showSuccessDialog(String message, String imagePath) {
        JDialog dialog = new JDialog(parentFrame, "Success", true);

        setupSuccessDialogComponents(dialog, message, imagePath);
        setupSuccessDialogButton(dialog);

        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    // EFFECTS: Sets up the components for the success dialog
    private void setupSuccessDialogComponents(JDialog dialog, String message, String imagePath) {
        dialog.setLayout(new BorderLayout(10, 10));

        JLabel imageLabel = new JLabel();
        ImageIcon thumbsUp = new ImageIcon(imagePath);
        if (thumbsUp.getIconWidth() > 0) {
            Image img = thumbsUp.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            thumbsUp = new ImageIcon(img);
            imageLabel.setIcon(thumbsUp);
        }
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);

        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.add(messageLabel, BorderLayout.SOUTH);
    }

    // EFFECTS: Sets up the OK button for the success dialog
    private void setupSuccessDialogButton(JDialog dialog) {
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    }

    // REQUIRES: Input string must be one of: RACECAR, SUPERCAR, SPORTSCAR, LUXURY,
    // MUSCLE,
    // VINTAGE, ECONOMY, OTHER (case sensitive)
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
}