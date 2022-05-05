package cz.cuni.mff.java.projects.graphqlapp.ui;

import graphql.ExecutionResult;
import graphql.GraphQL;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JPanel that displays population information about a given area.
 */
public class PopulationCard extends JPanel {

    /**
     * AreaListItem from which this Card was created. Return this to AreaPanel upon destruction.
     */
    private final AreaListItem source;
    /**
     * GraphQL Database instance to query
     */
    private final GraphQL graphQL;
    /**
     * Background colour of the card.
     */
    private final Color bgColor;
    /**
     * JScrollPane containing the demographic info. Necessary in case there are multiple years and fields,
     * so that the card does not extend out of scope of the window. Displays the yearDemsPanel.
     */
    private final JScrollPane demsScrollPane;
    /**
     * The JPanel which is being displayed by the demsScrollPane. When updating dems, add them here.
     */
    private JPanel yearDemsPanel;

    /**
     * Creates a JPanel card displaying selected information about the provided region
     *
     * @param source AreaListItem of area to display
     * @param areaPanel AreaPanel parent reference
     * @param bgColor background colour of card
     * @param selectedFields Strings that are being displayed. Must conform to schema!
     * @param graphQL endpoint to query
     */
    public PopulationCard(AreaListItem source, AreaPanel areaPanel, Color bgColor,
                          ArrayList<String> selectedFields, GraphQL graphQL) {
        this.bgColor = bgColor;
        this.graphQL = graphQL;
        this.source = source;
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.PAGE_START;

        this.add(new JLabel(source.name()), gbc);
        this.add(new JLabel("ID: " + source.id()), gbc);

        demsScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        updateYearDems(selectedFields);
        demsScrollPane.getVerticalScrollBar().setUnitIncrement(16);  // Increase scrollBar sensitivity

        // This will stretch dems scroll pane across the card
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(demsScrollPane, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        gbc.anchor = GridBagConstraints.PAGE_END;
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new AreaRemoveListener(areaPanel));
        this.add(removeButton, gbc);

        this.setBackground(bgColor);
    }

    /**
     * Updates the demographics JScrollPane to include only the schema fields provided.
     * Re-Queries the GraphQL Database for all requested fields and creates new Viewport JPanel for them.
     * @param selectedFields list of fields from schema to query
     */
    public void updateYearDems(ArrayList<String> selectedFields) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        ArrayList<LinkedHashMap<String, Integer>> queryDems = queryForFields(selectedFields);
        yearDemsPanel = new JPanel(new GridBagLayout());
        yearDemsPanel.setBackground(bgColor.brighter());

        for(LinkedHashMap<String, Integer> demYear: queryDems) {
            makeYearDems(demYear, gbc);
        }
        demsScrollPane.setViewportView(yearDemsPanel);
    }

    /**
     * Processes demographic info for one year and inserts it into the provided JPanel.
     * Use this to display per-year demographic info to user.
     * @param demYear Map of info fields to display. !Must include "year"!
     * @param gbc GridBagConstraints used for inserting to scrollPanePanel
     */
    private void makeYearDems(LinkedHashMap<String, Integer> demYear, GridBagConstraints gbc) {
        JLabel yearLabel = new JLabel(demYear.get("year").toString());
        yearLabel.setFont(new Font(yearLabel.getFont().getName(), Font.BOLD, 16));
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.insets = new Insets(10, 0, 0, 0);
        yearDemsPanel.add(yearLabel, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
        demYear.remove("year");

        for(Map.Entry<String, Integer> yearField: demYear.entrySet()) {
            String labelText = yearField.getKey() + ": " + yearField.getValue();
            yearDemsPanel.add(new JLabel(labelText), gbc);
        }
    }

    /**
     * queries the graphQL instance for the area which is being displayed and all the fields
     * which are selected to display.
     *
     * Make sure selectedFields conforms to the schema, else this will fail.
     * @param selectedFields fields which have been selected to query for
     * @return annual demographic fields from response
     */
    private ArrayList<LinkedHashMap<String, Integer>> queryForFields(ArrayList<String> selectedFields) {
        if (selectedFields.size() == 0) {
            return new ArrayList<>();
        }
        StringBuilder queryBuilder = new StringBuilder("{");
        String queryType;
        switch (source.areaType()) {
            case OBCE -> queryType = "obecById";
            case OKRESY -> queryType = "okresById";
            case KRAJE -> queryType = "krajById";
            default -> queryType = "";  // Mustn't happen
        }
        queryBuilder.append(queryType).append("(id: ");
        queryBuilder.append(source.id()).append("){ demographics { year ");
        for(String field: selectedFields) {
            queryBuilder.append(field).append(' ');
        }
        queryBuilder.append("} } }");

        ExecutionResult response = graphQL.execute(queryBuilder.toString());
        if (!response.getErrors().isEmpty()) {
            System.out.println("Error occurred during query.");
        }
        if (response.getData() != null) {
            // This is disgusting, I know, but I don't know how else to retrieve the data from the ExecutionResult
            var dems = (LinkedHashMap<String, LinkedHashMap<String,ArrayList<LinkedHashMap<String, Integer>>>>)response.getData();
            return dems.get(queryType).get("demographics");
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the source AreaListItem that the card was initialised with.
     * Used to return the AreaListItem to area selection list
     * @return source AreaListItem
     */
    public AreaListItem getSource() {
        return source;
    }
}
