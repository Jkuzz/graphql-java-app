package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;

public class AreaPanel {
    JPanel pickerPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    private AddAreaListener addAreaListener;
    public Color bgColor;

    /**
     * Gives access to the listener of the Add Area button, to set target JPanel once it's created
     * @return the add area listener
     */
    public AddAreaListener getAddAreaListener() {
        return addAreaListener;
    }

    public AreaPanel(Color bgCol) {
        bgColor = bgCol;
    }

    /**
     * Creates a panel that provides the list of available areas to query,
     * and an option to add them to the display panel.
     *
     * !Must set AddAreaListener target panel before use!
     * @return the panel
     */
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

        pickerPanel.add(makeAreasScrollPane(), gbc);

        gbc.weighty = 0;
        gbc.insets.bottom = 10;
        pickerPanel.add(makeAreaBottomButtons(), gbc);

        pickerPanel.setBackground(new Color(200, 100, 100));
        return pickerPanel;
    }

    /**
     * Creates a panel containing the area type (codebook) switcher buttons
     * @return Panel with the buttons
     */
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


    /**
     * Creates a panel containing the 'add area' button
     * @return Panel containing the button
     */
    private JPanel makeAreaBottomButtons() {
        JPanel btnPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(addAreaListener);
        btnPanel.add(addButton);
        btnPanel.setBackground(bgColor);
        return btnPanel;
    }


    /**
     * Creates a JScrollPane containing a list of available areas to display
     * @return the scroll pane
     */
    private JScrollPane makeAreasScrollPane() {
        DefaultListModel<AreaListItem> dummyList = new DefaultListModel<>();
        for (int i=0; i<=30; i+=1) {
            dummyList.addElement(new AreaListItem("Area " + i, ""+i));
        }
        JList<AreaListItem> areasList = new JList<>(dummyList);

        addAreaListener = new AddAreaListener(areasList);
        return new JScrollPane(areasList);
    }

}
