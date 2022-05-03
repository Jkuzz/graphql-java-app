package cz.cuni.mff.java.projects.graphqlapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public record FieldsChosenListener(
        PopulationPanel populationPanel) implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        populationPanel.updateSelectedFields();
    }
}
