package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveAreaListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton sourceButton = (JButton) actionEvent.getSource();
        JPanel areaPanel = (JPanel) sourceButton.getParent();
        JPanel parentPanel = (JPanel) areaPanel.getParent();
        parentPanel.remove(areaPanel);
        parentPanel.revalidate();
    }
}
