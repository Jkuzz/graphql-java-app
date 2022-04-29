package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class AreaPanel {
    JPanel pickerPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    public Color bgColor;

    public AreaPanel(Color bgCol) {
        bgColor = bgCol;
    }

    public JPanel makeAreaPanel() {
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(3, 20, 3, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pickerPanel.add(makeAreaButtons(), gbc);

        JTextField areaNameSearch = new JTextField();
        gbc.gridy = GridBagConstraints.RELATIVE;
        pickerPanel.add(areaNameSearch, gbc);

        gbc.weighty = 1;
        pickerPanel.add(makeAreasList(), gbc);

        gbc.weighty = 0;
        gbc.insets.bottom = 10;
        pickerPanel.add(makeAreaBottomButtons(), gbc);

        pickerPanel.setBackground(new Color(200, 90, 90));
        return pickerPanel;
    }

    private JPanel makeAreaButtons() {
        JPanel btnPanel = new JPanel();
        JButton krajeButton = new JButton("Kraje");
        JButton okresyButton = new JButton("Okresy");
        JButton obceButton = new JButton("Obce");
        btnPanel.add(krajeButton);
        btnPanel.add(okresyButton);
        btnPanel.add(obceButton);

        btnPanel.setBackground(bgColor);
        return btnPanel;
    }


    private JPanel makeAreaBottomButtons() {
        JPanel btnPanel = new JPanel();
        JButton addButton = new JButton("Add");
        btnPanel.add(addButton);
        btnPanel.setBackground(bgColor);
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
