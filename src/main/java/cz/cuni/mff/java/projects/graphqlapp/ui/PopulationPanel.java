package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class PopulationPanel {
    final JPanel displayPanel = new JPanel(new GridBagLayout());
    final JPanel selectPanel = makeSelectPanel();
    private final AreaPanel areaPanel;
    GridBagConstraints gbc = new GridBagConstraints();

    public PopulationPanel(AreaPanel areaPanel) {
        this.areaPanel = areaPanel;

        displayPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        displayPanel.add(selectPanel, gbc);

        gbc.insets = new Insets(20, 5, 0, 5);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weighty = 0.8;
    }

    JPanel makePopDisplay() {
        displayPanel.setBackground(new Color(100, 100, 200));
        return displayPanel;
    }

    private JPanel makeSelectPanel() {
        JPanel selectPanel = new JPanel(new GridBagLayout());
        selectPanel.add(new JLabel("Select fields to display"));
        selectPanel.add(new JButton("Test"));
        selectPanel.setBackground(new Color(120, 120, 210));
        return selectPanel;
    }

    public void addArea(AreaListItem areaToAdd) {
        assert areaPanel != null;
        PopulationDisplay popDisplay = new PopulationDisplay(areaToAdd, areaPanel);

        gbc.gridx = GridBagConstraints.RELATIVE;
        displayPanel.add(popDisplay, gbc);
        displayPanel.revalidate();
    }
}
