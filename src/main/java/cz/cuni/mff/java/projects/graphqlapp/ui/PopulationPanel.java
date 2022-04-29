package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class PopulationPanel {
    final JPanel displayPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    public PopulationPanel() {
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
    }

    JPanel makePopDisplay() {
        displayPanel.setBackground(new Color(100, 100, 200));
        return displayPanel;
    }

    void addArea(JPanel areaPanel) {
        gbc.gridx = GridBagConstraints.RELATIVE;
        displayPanel.add(areaPanel, gbc);
        displayPanel.revalidate();
    }
}
