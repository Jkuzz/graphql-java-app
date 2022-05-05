package cz.cuni.mff.java.projects.graphqlapp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener for the AreaPanel which adds the currently-selected areas as new PopulationCards to the PopulationPanel.
 */
public final class AreaAddListener implements ActionListener {
    private PopulationPanel populationPanel = null;
    private final AreaPanel areaPanel;

    /**
     * Create a listener that adds the areas selected form the list to the population panel.
     * @param areaPanel AreaPanel to remove from
     */
    public AreaAddListener(AreaPanel areaPanel) {
        this.areaPanel = areaPanel;
    }

    /**
     * Add area button callback function
     *
     * Instructs display panel to add the selected areas to the display
     * and removes them from the selection list.
     * @param actionEvent swing action event
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        assert populationPanel != null;

        for(AreaListItem item: areaPanel.getAreaList().getSelectedValuesList()) {
            populationPanel.addArea(item);
            areaPanel.removeItem(item);
        }

        areaPanel.getAreaList().clearSelection();
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
