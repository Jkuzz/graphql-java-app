package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class AreaPanel {
    static JPanel makeAreaPanel() {
        JPanel pickerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 20, 3, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        pickerPanel.add(makeAreaButtons(), gbc);

        gbc.gridy = GridBagConstraints.RELATIVE;
        JTextField areaNameSearch = new JTextField();
        pickerPanel.add(areaNameSearch, gbc);

        gbc.weighty = 1;
        pickerPanel.add(makeAreasList(), gbc);

        gbc.weighty = 0;
        gbc.insets.bottom = 10;
        pickerPanel.add(makeAreaBottomButtons(), gbc);

        pickerPanel.setBackground(new Color(200, 90, 90));
        return pickerPanel;
    }

    private static JPanel makeAreaButtons() {
        JPanel btnPanel = new JPanel();
        JButton krajeButton = new JButton("Kraje");
        JButton okresyButton = new JButton("Okresy");
        JButton obceButton = new JButton("Obce");
        btnPanel.add(krajeButton);
        btnPanel.add(okresyButton);
        btnPanel.add(obceButton);

        btnPanel.setBackground(new Color(200, 90, 90));
        return btnPanel;
    }


    private static JPanel makeAreaBottomButtons() {
        JPanel btnPanel = new JPanel();
        JButton addButton = new JButton("Add");
        btnPanel.add(addButton);
        btnPanel.setBackground(new Color(200, 90, 90));
        return btnPanel;
    }


    private static JScrollPane makeAreasList() {
        DefaultListModel<String> dummyList = new DefaultListModel<>();
        dummyList.add(dummyList.getSize(), "Praha");
        dummyList.add(dummyList.getSize(), "Zbytek");
        for (int i=0; i<=30; i+=1) {
            dummyList.add(dummyList.getSize(), "Area " + i);
        }
        JList<String> areasList = new JList<>(dummyList);

        JScrollPane scrollPane = new JScrollPane(areasList);
        scrollPane.setPreferredSize(new Dimension(100, 100));

        return scrollPane;
    }

}
