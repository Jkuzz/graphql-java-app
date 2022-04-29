package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record RemoveAreaListener(
        AreaPanel areaPanel) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton sourceButton = (JButton) actionEvent.getSource();
        PopulationDisplay areaInfo = (PopulationDisplay) sourceButton.getParent();
        JPanel parentPanel = (JPanel) areaInfo.getParent();
        parentPanel.remove(areaInfo);
        parentPanel.revalidate();

        areaPanel.addItem(areaInfo.getSource());
    }
}
