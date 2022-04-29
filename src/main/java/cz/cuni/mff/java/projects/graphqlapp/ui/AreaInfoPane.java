package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class AreaInfoPane extends JPanel {

    public AreaInfoPane(AreaListItem source, JList<AreaListItem> areaList) {
        this.source = source;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JLabel(source.name()));
        this.add(new JLabel(source.id()));

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new RemoveAreaListener(areaList));
        this.add(removeButton);

        this.setBackground(new Color(120, 120, 210));
    }

    public AreaListItem getSource() {
        return source;
    }

    private final AreaListItem source;
}
