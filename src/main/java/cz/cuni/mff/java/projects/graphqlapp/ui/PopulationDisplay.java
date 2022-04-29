package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class PopulationDisplay extends JPanel {

    public PopulationDisplay(AreaListItem source, AreaPanel areaPanel) {
        this.source = source;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JLabel(source.name()));
        this.add(new JLabel(source.id()));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveAreaListener(areaPanel));
        this.add(removeButton);

        this.setBackground(new Color(120, 120, 210));
    }

    public AreaListItem getSource() {
        return source;
    }

    private final AreaListItem source;
}
