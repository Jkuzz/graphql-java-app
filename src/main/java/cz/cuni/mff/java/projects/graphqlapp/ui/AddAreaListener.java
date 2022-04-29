package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public final class AddAreaListener implements ActionListener {
    private final JList<AreaListItem> areasList;
    private PopulationPanel populationPanel = null;

    /**
     * Create a listener that adds the areas selected form the list to the population panel
     * @param areasList UI list of areas
     */
    public AddAreaListener(JList<AreaListItem> areasList) {
        this.areasList = areasList;
    }

    /**
     * Add area button callback function
     *
     * Instructs display panel to add the selected areas to the display
     * and removes them from the selection list
     * @param actionEvent swing action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        assert populationPanel != null;
        DefaultListModel<AreaListItem> areasModel = (DefaultListModel<AreaListItem>) areasList.getModel();

        ArrayList<Integer> selectedIndices = new ArrayList<>();
        for(int index: areasList.getSelectedIndices()) {  // For some reason must iterate, other methods don't work
            selectedIndices.add(index);
        }

        for(int selectedIndex: selectedIndices) {
            populationPanel.addArea(areasModel.elementAt(selectedIndex));
        }

        // Must remove in reverse order!
        Collections.reverse(selectedIndices);
        for(int selectedIndex: selectedIndices) {
            areasModel.remove(selectedIndex);
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
