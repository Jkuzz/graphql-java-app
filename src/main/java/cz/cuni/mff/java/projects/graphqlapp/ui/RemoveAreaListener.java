package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveAreaListener implements ActionListener {
    private final JList<AreaListItem> areaList;

    public RemoveAreaListener(JList<AreaListItem> areaList) {
        this.areaList = areaList;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton sourceButton = (JButton) actionEvent.getSource();
        AreaInfoPane areaPanel = (AreaInfoPane) sourceButton.getParent();
        JPanel parentPanel = (JPanel) areaPanel.getParent();
        parentPanel.remove(areaPanel);
        parentPanel.revalidate();

        DefaultListModel<AreaListItem> areaListModel = (DefaultListModel<AreaListItem>) areaList.getModel();
        areaListModel.addElement(areaPanel.getSource());

        AreaPanel.sortAreaListModel(areaListModel);
    }
}
