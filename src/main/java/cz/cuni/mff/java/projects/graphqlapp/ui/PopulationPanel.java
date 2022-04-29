package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class PopulationPanel {
    static JPanel makePopDisplay() {
        JPanel displayPanel = new JPanel(new GridBagLayout());

        JLabel conversionOutLabel = new JLabel("population display");
        displayPanel.add(conversionOutLabel);

        displayPanel.setBackground(new Color(100, 100, 200));
        return displayPanel;
    }
}
