package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class App {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Root");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(createContent());

        frame.setMinimumSize(new Dimension(800, 500));
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel createContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 20, 15, 10);
        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1;

        JPanel pickerPanel = makeAreaPicker();
        contentPanel.add(pickerPanel, gbc);

        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 10, 15, 20);
        gbc.weightx = 0.85;
        JPanel displayPanel = makePopDisplay();
        contentPanel.add(displayPanel, gbc);
        return contentPanel;
    }

    private static JPanel makeAreaPicker() {
        JPanel pickerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 20, 3, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        pickerPanel.add(makeAreaButtons(), gbc);

        gbc.gridy = GridBagConstraints.RELATIVE;
        JTextField areaNameSearch = new JTextField();
        pickerPanel.add(areaNameSearch, gbc);

        gbc.insets.bottom = 20;
        gbc.weighty = 1;
        pickerPanel.add(makeAreasList(), gbc);

        pickerPanel.setBackground(new Color(200, 90, 90));
        return pickerPanel;
    }

    private static JPanel makeAreaButtons() {
        JPanel btnPanel = new JPanel();
        JButton krajeButton = new JButton("Kraje");
        JButton okresyButton = new JButton("Okresy");
        JButton obceButton = new JButton("Obce");
        btnPanel.add(krajeButton);
        btnPanel.add(okresyButton);
        btnPanel.add(obceButton);

        btnPanel.setBackground(new Color(200, 90, 90));
        return btnPanel;
    }


    private static JScrollPane makeAreasList() {
        String[] dummyList = {"Praha", "Zbytek"};
        JList<String> areasList = new JList<>(dummyList);

        JScrollPane scrollPane = new JScrollPane(areasList);
        scrollPane.setPreferredSize(new Dimension(100, 100));

        return scrollPane;
    }


    private static JPanel makePopDisplay() {
        JPanel displayPanel = new JPanel(new GridBagLayout());

        JLabel conversionOutLabel = new JLabel("population display");
        displayPanel.add(conversionOutLabel);

        displayPanel.setBackground(new Color(100, 100, 200));
        return displayPanel;
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(App::createAndShowGUI);
    }
}
