package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class AddAreaListener implements ActionListener {
    private final JList<String> areasList;
    private PopulationPanel populationPanel = null;

    public AddAreaListener(JList<String> areasList) {
        this.areasList = areasList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        assert populationPanel != null;
        JPanel dummyPanel = new JPanel();
        dummyPanel.add(new JLabel(areasList.getSelectedValue()));
        populationPanel.addArea(dummyPanel);
    }

    public void setPopulationPanel(PopulationPanel populationPanel) {
        this.populationPanel = populationPanel;
    }
}
