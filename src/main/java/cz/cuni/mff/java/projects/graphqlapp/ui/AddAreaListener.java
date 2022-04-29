package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class AddAreaListener implements ActionListener {
    private final JList<String> areasList;
    private PopulationPanel populationPanel = null;

    /**
     * Create a listener that adds the areas selected form the list to the population panel
     * @param areasList UI list of areas
     */
    public AddAreaListener(JList<String> areasList) {
        this.areasList = areasList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        assert populationPanel != null;
        DefaultListModel<String> areasModel = (DefaultListModel<String>) areasList.getModel();

        for(int selectedIndex: areasList.getSelectedIndices()) {
            JPanel dummyPanel = new JPanel();
            dummyPanel.add(new JLabel(areasModel.getElementAt(selectedIndex)));
            areasModel.remove(selectedIndex);
            populationPanel.addArea(dummyPanel);
        }
        areasList.clearSelection();
    }

    /**
     * Set the listener's target to display added areas to.
     * The target must be set before listener is first called!
     * @param populationPanel target panel to add to
     */
    public void setPopulationPanel(PopulationPanel populationPanel) {
        this.populationPanel = populationPanel;
    }
}
