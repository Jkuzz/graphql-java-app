package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record AreaRemoveListener(
        AreaPanel areaPanel) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton sourceButton = (JButton) actionEvent.getSource();
        PopulationCard areaInfo = (PopulationCard) sourceButton.getParent();
        PopulationPanel parentPanel = (PopulationPanel) areaInfo.getParent();
        parentPanel.removeArea(areaInfo);

        areaPanel.addItem(areaInfo.getSource());
    }
}
