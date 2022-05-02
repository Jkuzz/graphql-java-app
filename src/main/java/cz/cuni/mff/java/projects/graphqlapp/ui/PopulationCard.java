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
        gbc.anchor = GridBagConstraints.PAGE_START;

        this.add(new JLabel(source.name()), gbc);
        this.add(new JLabel("ID: " + source.id()), gbc);

        ArrayList<LinkedHashMap<String, Integer>> queryDems = queryForFields(selectedFields);
        for(LinkedHashMap<String, Integer> demYear: queryDems) {
            makeYearDemsPanel(demYear, gbc);
        }

        // This will make button stay at bottom and rest on top of the card
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new AreaRemoveListener(areaPanel));
        this.add(removeButton, gbc);

        this.setBackground(bgColor);
    }

    private void makeYearDemsPanel(LinkedHashMap<String, Integer> demYear, GridBagConstraints gbc) {
        JLabel yearLabel = new JLabel(demYear.get("year").toString());
        yearLabel.setFont(new Font(yearLabel.getFont().getName(), Font.BOLD, 16));
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(yearLabel, gbc);
        demYear.remove("year");

        System.out.println(demYear);

        for(Map.Entry<String, Integer> yearField: demYear.entrySet()) {
            String labelText = yearField.getKey() + ": " + yearField.getValue();
            this.add(new JLabel(labelText), gbc);
        }
    }

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

    public AreaListItem getSource() {
        return source;
    }

}
