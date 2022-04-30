package cz.cuni.mff.java.projects.graphqlapp.ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AreaPanel {
    JPanel pickerPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    private AddAreaListener addAreaListener;
    private String searchFilter = "";
    private JList<AreaListItem> areaList;
    private ArrayList<AreaListItem> baseListContent = new ArrayList<>();
    public Color bgColor;


    public JList<AreaListItem> getAreaList() {
        return areaList;
    }


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

        areaNameSearch.getDocument().addDocumentListener(new SearchChangeListener(this));

        gbc.weighty = 0;
        gbc.insets.bottom = 10;
        pickerPanel.add(makeAreaBottomButtons(), gbc);

        pickerPanel.setBackground(bgColor);
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
        AreaLoader areaLoader = new AreaLoader();
        ArrayList<AreaListItem> dummyList = areaLoader.loadArea(AreaLoader.AreaType.KRAJE);

        areaList = new JList<>();
        baseListContent = dummyList;
        setSearchFilter("");

        addAreaListener = new AddAreaListener(this);
        return new JScrollPane(areaList);
    }


    /**
     * Iterates the base AreaListItem's, sorts them alphabetically,
     * filters them according to searchFilter and updates the area list model to display them.
     */
    public void processAreaListModel() {
        System.out.println(searchFilter);
        baseListContent.sort(Comparator.comparing(AreaListItem::name));

        DefaultListModel<AreaListItem> processedModel = new DefaultListModel<>();
        for(AreaListItem item: baseListContent) {
            if(item.name().startsWith(searchFilter)) {
                processedModel.addElement(item);
            }
        }
        areaList.setModel(processedModel);
    }

    /**
     * Change the search filter and update view
     * @param filter new filter
     */
    public void setSearchFilter(String filter) {
        this.searchFilter = filter;
        processAreaListModel();
    }

    /**
     * Remove list item from base items and update view
     * @param item to remove
     */
    public void removeItem(AreaListItem item) {
        baseListContent.remove(item);
        processAreaListModel();
    }

    /**
     * Add list item to base items and update view
     * @param item to add
     */
    public void addItem(AreaListItem item) {
        baseListContent.add(item);
        processAreaListModel();
    }

}
