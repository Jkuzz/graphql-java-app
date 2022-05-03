package cz.cuni.mff.java.projects.graphqlapp.ui;

import graphql.GraphQL;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLSchemaElement;
import graphql.schema.GraphQLType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PopulationPanel extends JPanel {
    final JPanel selectPanel;
    private final AreaPanel areaPanel;
    private final GraphQL graphQL;
    private final Color BG_COLOR_LIGHT = new Color(130, 130, 210);
    private final HashMap<String, Boolean> selectedFields = new HashMap<>();
    GridBagConstraints gbc = new GridBagConstraints();

    public PopulationPanel(AreaPanel areaPanel, GraphQL graphQL) {
        this.setLayout(new GridBagLayout());
        this.areaPanel = areaPanel;
        this.graphQL = graphQL;
        this.selectPanel = makeSelectPanel();

        this.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.add(selectPanel, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 5, 0, 5);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weighty = 1;
        this.setBackground(new Color(100, 100, 200));
    }

    private JPanel makeSelectPanel() {
        JPanel selectPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(
                0, 0, 1, 1, 1, 0, GridBagConstraints.PAGE_START,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0
        );
        selectPanel.add(new JLabel("Select fields to display"), gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        selectPanel.add(makeCheckboxPanel(), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        selectPanel.add(new JButton("Select"), gbc);
        selectPanel.setBackground(BG_COLOR_LIGHT);
        return selectPanel;
    }

    private JPanel makeCheckboxPanel() {
        GraphQLType demSchema = graphQL.getGraphQLSchema().getType("Demographics");
        assert demSchema != null;
        List<GraphQLSchemaElement> schemaElements = demSchema.getChildren();
        JPanel checkPanel = new JPanel(new GridLayout(schemaElements.size() / 3, 3));

        for(GraphQLSchemaElement element: schemaElements) {
            GraphQLFieldDefinition fieldDef = (GraphQLFieldDefinition) element;
            if(fieldDef.getName().equals("year")) {
                continue;  // Year is automatically queried for
            }
            JCheckBox fieldCheckBox = new JCheckBox(fieldDef.getName());
            selectedFields.put(fieldDef.getName(), false);
            fieldCheckBox.setOpaque(false);
            checkPanel.add(fieldCheckBox);
        }

        this.selectedFields.put("deaths", true); // TODO: Implement field selecting
        this.selectedFields.put("immigrations", true); // TODO: Implement field selecting
        this.selectedFields.put("emigrations", true); // TODO: Implement field selecting
        this.selectedFields.put("natGrowth", true); // TODO: Implement field selecting
        this.selectedFields.put("totalGrowth", true); // TODO: Implement field selecting
        this.selectedFields.put("births", true); // TODO: Implement field selecting
        this.selectedFields.put("migSaldo", true); // TODO: Implement field selecting
        this.selectedFields.put("popMean", true); // TODO: Implement field selecting

        checkPanel.setBackground(BG_COLOR_LIGHT);
        return checkPanel;
    }

    public void addArea(AreaListItem areaToAdd) {
        assert areaPanel != null;
        PopulationCard popCard = new PopulationCard(areaToAdd, areaPanel, BG_COLOR_LIGHT, selectedFieldsToArray(), graphQL);

        gbc.gridx = GridBagConstraints.RELATIVE;
        this.add(popCard, gbc);
        this.revalidate();
    }

    private ArrayList<String> selectedFieldsToArray() {
        ArrayList<String> selected = new ArrayList<>();
        for(Map.Entry<String, Boolean> e: selectedFields.entrySet()) {
            if(e.getValue()) {
                selected.add(e.getKey());
            }
        }
        return selected;
    }
}
