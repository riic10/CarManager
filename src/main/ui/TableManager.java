package ui;

import model.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

// Manages the filling and updating of car tables
public class TableManager {
    private DefaultTableModel tableModel;
    private String[] columnNames;

    public TableManager(DefaultTableModel tableModel, String[] columnNames) {
        this.tableModel = tableModel;
        this.columnNames = columnNames;
    }

    // EFFECTS: Adds each car in the collection as a row in the table
    public void fillTable(Collection collection) {
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

    // EFFECTS: populates rows of the table by going through each car in the
    //          given list of cars
    public void fillTable(DefaultTableModel model, ArrayList<Car> cars) {
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
    public void refreshTable(Collection collection) {
        tableModel.setRowCount(0);
        
        if (collection != null) {
            fillTable(collection);
        }
    }
}