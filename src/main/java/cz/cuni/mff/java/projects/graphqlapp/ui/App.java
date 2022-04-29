package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class App {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Population View");
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
        gbc.weightx = 0.1;
        gbc.weighty = 1;

        AreaPanel areaPanel = new AreaPanel(new Color(200, 100, 100));
        JPanel pickerPanel = areaPanel.makeAreaPanel();
        contentPanel.add(pickerPanel, gbc);

        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 10, 15, 20);
        gbc.weightx = 0.9;
        PopulationPanel popPanel = new PopulationPanel();
        JPanel displayPanel = popPanel.makePopDisplay();
        contentPanel.add(displayPanel, gbc);
        return contentPanel;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(App::createAndShowGUI);
    }
}
