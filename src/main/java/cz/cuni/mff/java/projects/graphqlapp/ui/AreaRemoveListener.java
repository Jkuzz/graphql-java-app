package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener for the PopulationCards' remove button. Handles removing the area card
 * as well as returning it back to the area choice list.
 */
public record AreaRemoveListener(
        AreaPanel areaPanel) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton sourceButton = (JButton) actionEvent.getSource();
        PopulationCard areaInfo = (PopulationCard) sourceButton.getParent();
        PopulationPanel parentPanel = (PopulationPanel) areaInfo.getParent();
        parentPanel.removeArea(areaInfo);
        parentPanel.getParent().revalidate();

        areaPanel.addItem(areaInfo.getSource());
    }
}
