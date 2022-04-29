package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class App {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Population");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel converterPanel = new JPanel(new GridBagLayout());
        converterPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField inputField = new JTextField();
        converterPanel.add(inputField);
        JLabel conversionOutLabel = new JLabel("0");
        converterPanel.add(conversionOutLabel);


        frame.getContentPane().add(converterPanel);
        frame.setMinimumSize(new Dimension(250, 180));
        frame.pack();
        frame.setVisible(true);
    }

    private static JFrame makeAreaPicker() {
        return null;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
