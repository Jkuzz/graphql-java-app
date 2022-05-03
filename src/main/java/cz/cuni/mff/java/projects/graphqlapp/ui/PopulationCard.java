package cz.cuni.mff.java.projects.graphqlapp.ui;

import graphql.ExecutionResult;
import graphql.GraphQL;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PopulationCard extends JPanel {

    private final AreaListItem source;
    private final GraphQL graphQL;

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

        ArrayList<LinkedHashMap<String, Integer>> queryDems = queryForFields(selectedFields);
        JPanel scrollViewportPanel = new JPanel(new GridBagLayout());
        for(LinkedHashMap<String, Integer> demYear: queryDems) {
            makeYearDemsPanel(demYear, scrollViewportPanel, gbc);
        }
        scrollViewportPanel.setBackground(bgColor.brighter());
        JScrollPane demsScrollPane = new JScrollPane(scrollViewportPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
     * Processes demographic info for one year and inserts it into the provided JPanel.
     * Use this to display per-year demographic info to user.
     * @param demYear Map of info fields to display. !Must include "year"!
     * @param scrollPanePanel panel to insert labels into
     * @param gbc GridBagConstraints used for inserting to scrollPanePanel
     */
    private void makeYearDemsPanel(LinkedHashMap<String, Integer> demYear, JPanel scrollPanePanel, GridBagConstraints gbc) {
        JLabel yearLabel = new JLabel(demYear.get("year").toString());
        yearLabel.setFont(new Font(yearLabel.getFont().getName(), Font.BOLD, 16));
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);

        scrollPanePanel.add(yearLabel, gbc);
        demYear.remove("year");

        for(Map.Entry<String, Integer> yearField: demYear.entrySet()) {
            String labelText = yearField.getKey() + ": " + yearField.getValue();
            scrollPanePanel.add(new JLabel(labelText), gbc);
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
        switch (source.areaType()) {
            case OBCE -> queryBuilder.append("obecById(id: ");
            case OKRESY -> queryBuilder.append("okresById(id: ");
            case KRAJE -> queryBuilder.append("krajById(id: ");
        }
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
            // This is disgusting, I know, but I don't know how else to retrieve the data
            var dems = (LinkedHashMap<String, LinkedHashMap<String,ArrayList<LinkedHashMap<String, Integer>>>>)response.getData();
            return dems.get("krajById").get("demographics");
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
