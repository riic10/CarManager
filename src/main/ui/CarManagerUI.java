package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CarManagerUI {
    private JFrame frame;
    private JPanel panel;

    private Collection collection;
    private static final String JSON_STORE = "./data/collection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public CarManagerUI() {
        int choice = JOptionPane.showConfirmDialog(
                null, 
                "Would you like to load existing car data?", 
                null, 
                JOptionPane.YES_NO_OPTION
        );
    
        if (choice == JOptionPane.YES_OPTION) {
            System.out.println("User chose YES");
            // Open the program with data loaded from file
        } else {
            System.out.println("User chose NO");
            // Open the program without loading from file
        }

        frame = new JFrame();
        panel = new JPanel();
        createToolBar();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        panel.setBackground(Color.decode("#C9A0DC"));
        frame.add(panel);
        frame.setVisible(true);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
    
        JButton addCarButton = new JButton("Add Car");
        JButton removeCarButton = new JButton("Remove Car");
        JButton filterForSaleButton = new JButton("Show cars for sale");
        toolBar.add(addCarButton);
        toolBar.add(removeCarButton);
        toolBar.add(filterForSaleButton);

        addCarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Add Car clicked!"));
        removeCarButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Remove Car clicked!"));
        filterForSaleButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Filter for sale clicked!"));

        frame.add(toolBar, BorderLayout.NORTH);
    }
}