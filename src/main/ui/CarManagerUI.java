package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CarManagerUI {
    private JFrame frame;
    private JPanel panel;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        panel.setBackground(Color.decode("#C9A0DC"));
        frame.add(panel);
        frame.setVisible(true);
    }
}