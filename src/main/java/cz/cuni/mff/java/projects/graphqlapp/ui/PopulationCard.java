package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class PopulationCard extends JPanel {

    public PopulationCard(AreaListItem source, AreaPanel areaPanel, Color bgColor) {
        this.source = source;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JLabel(source.name()));
        this.add(new JLabel(source.id()));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveAreaListener(areaPanel));
        this.add(removeButton);

        this.setBackground(bgColor);
    }

    public AreaListItem getSource() {
        return source;
    }

    private final AreaListItem source;
}
