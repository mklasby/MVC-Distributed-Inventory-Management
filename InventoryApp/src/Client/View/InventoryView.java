package Client.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;

public class InventoryView extends SubView {
    public Gui gui;
    public JPanel inventoryPanel;
    public JPanel searchQueryPanel;
    public JPanel inventoryInfoPanel;
    public JPanel searchResultsPanel;
    public JPanel posPanel;
    public JLabel searchInvLabel;
    public JLabel searchTypeLabel;
    public JLabel searchQueryLabel;
    public JLabel searchResultsLabel;
    public JLabel inventoryInfoLabel;
    public JLabel toolIdLabel;
    public JLabel toolNameLabel;
    public JLabel stockLabel;
    public JLabel priceLabel;
    public JLabel supplierIdLabel;
    public JLabel toolTypeLabel;
    public JLabel posLabel;
    public JLabel quantityLabel;
    public JRadioButton idButton;
    public JRadioButton nameButton;
    public JRadioButton typeButton;
    public ButtonGroup buttonGroup;
    public JTextField searchQueryField;
    public JTextField toolIdField;
    public JTextField toolNameField;
    public JTextField stockField;
    public JTextField priceField;
    public JTextField supplierIdField;
    public JTextField toolTypeField;
    public JTextField quantityField;
    public JComboBox<String> toolTypeComboBox;
    public JButton searchButton;
    public JButton clearSearchButton;
    public JButton searchAllButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton clearButton;
    public JButton sellTool;
    public JButton returnTool;
    public JButton generateOrder;
    public JButton addTool;
    public JList<String> resultsList;
    public DefaultListModel<String> listModel;
    public HashMap<String, JTextField> fields;

    public InventoryView() {
        super();
        this.buildGui();
    }

    public InventoryView(Gui gui) {
        super(gui);
        this.buildGui();
        super.gui.addCard(inventoryPanel, "inventoryPanel");
    }

    private void buildGui() {
        buildSearchQueryPanel();
        buildSearchResultsPanel();
        buildInventoryInfoPanel();
        buildPosPanel();
        buildInventoryPanel();
        addFields();
        generateTestList();
    }

    private void buildPosPanel() {
        posPanel = new JPanel();
        posPanel.setLayout(new GridBagLayout());
        posPanel.setSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION / 2));
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 100, 0, 3); // top, right, bottom, left;
        posLabel = new JLabel("Point of Sale");
        posLabel.setFont(ViewConstants.SUBTITLE_FONT);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        posPanel.add(posLabel, c);

        quantityLabel = new JLabel("Quantity: ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        posPanel.add(quantityLabel, c);

        quantityField = new JTextField(15);
        quantityField.setActionCommand("quantityField");
        c.gridx = 1;
        c.gridy = 1;
        posPanel.add(quantityField, c);

        sellTool = new JButton("Sell Tool");
        sellTool.setActionCommand("sellTool");
        c.insets = new Insets(3, 0, 3, 0); // top, right, bottom, left;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        posPanel.add(sellTool, c);

        returnTool = new JButton("Return Tool");
        returnTool.setActionCommand("returnTool");
        c.gridy = 3;
        posPanel.add(returnTool, c);

        generateOrder = new JButton("Generate Order");
        generateOrder.setActionCommand("generateOrder");
        c.gridy = 4;
        posPanel.add(generateOrder, c);
    }

    @Override
    public void display() {
        super.gui.setPanel("inventoryPanel");
    }

    private void addFields() {
        fields = new HashMap<String, JTextField>();
        this.fields.put("searchQueryField", searchQueryField);
        this.fields.put("toolIdField", toolIdField);
        this.fields.put("toolNameField", toolNameField);
        this.fields.put("stockField", stockField);
        this.fields.put("priceField", priceField);
        this.fields.put("supplierIdField", supplierIdField);
        this.fields.put("quantity", quantityField);
    }

    public String getquantityFieldText() {
        return quantityField.getText();
    }

    public String getFieldText(String fieldName) {
        return fields.get(fieldName).getText();
    }

    private void buildSearchQueryPanel() {
        searchQueryPanel = new JPanel();
        searchQueryPanel.setLayout(new GridBagLayout());
        searchQueryPanel.setSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION / 2));
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 3, 10, 3); // top, right, bottom, left;

        searchInvLabel = new JLabel("Search Inventory");
        searchInvLabel.setFont(ViewConstants.SUBTITLE_FONT);
        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        searchQueryPanel.add(searchInvLabel, c);

        searchTypeLabel = new JLabel("Select type of search to be performed:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 3;
        searchQueryPanel.add(searchTypeLabel, c);

        idButton = new JRadioButton("Tool ID");
        idButton.setSelected(true);
        idButton.setActionCommand("id");
        nameButton = new JRadioButton("Tool Name / Description");
        nameButton.setActionCommand("name");
        typeButton = new JRadioButton("Tool Type");
        typeButton.setActionCommand("type");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(idButton);
        buttonGroup.add(nameButton);
        buttonGroup.add(typeButton);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 3;
        searchQueryPanel.add(idButton, c);
        c.gridy = 3;
        searchQueryPanel.add(nameButton, c);
        c.gridy = 4;
        searchQueryPanel.add(typeButton, c);

        c.insets = new Insets(10, 3, 0, 3); // top, right, bottom, left;
        searchQueryLabel = new JLabel("Enter Search Parameter below:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 3;
        searchQueryPanel.add(searchQueryLabel, c);

        c.insets = new Insets(0, 3, 0, 3); // top, right, bottom, left;
        searchQueryField = new JTextField(15);
        searchQueryField.setEditable(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        searchQueryPanel.add(searchQueryField, c);

        c.insets = new Insets(0, 3, 10, 0); // top, right, bottom, left;
        searchButton = new JButton("Search");
        searchButton.setActionCommand("search");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        searchQueryPanel.add(searchButton, c);

        c.insets = new Insets(0, 0, 10, 0); // top, right, bottom, left;
        searchAllButton = new JButton("Search All");
        searchAllButton.setActionCommand("searchAll");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        searchQueryPanel.add(searchAllButton, c);

        c.insets = new Insets(0, 0, 10, 3); // top, right, bottom, left;
        clearSearchButton = new JButton("Clear Search");
        clearSearchButton.setActionCommand("clearSearch");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        searchQueryPanel.add(clearSearchButton, c);
    }

    private void buildSearchResultsPanel() {
        searchResultsPanel = new JPanel();
        searchResultsPanel.setSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION / 2));
        searchResultsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 3, 10, 0); // top, right, bottom, left;
        c.weightx = 0.5;
        c.weighty = 1.0;

        searchResultsLabel = new JLabel("Search Results:");
        searchResultsLabel.setFont(ViewConstants.SUBTITLE_FONT);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        searchResultsPanel.add(searchResultsLabel, c);

        c.insets = new Insets(0, 3, 10, 3); // top, right, bottom, left;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        listModel = new DefaultListModel<String>();
        resultsList = new JList<String>(listModel);
        resultsList.setPreferredSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION / 2 - 10));
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        resultsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        searchResultsPanel.add(resultsList, c);
    }

    private void buildInventoryInfoPanel() {
        inventoryInfoPanel = new JPanel();
        inventoryInfoPanel.setLayout(new GridBagLayout());
        inventoryInfoPanel.setSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION / 2));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 4;
        inventoryInfoLabel = new JLabel("Tool Information:");
        inventoryInfoLabel.setFont(ViewConstants.SUBTITLE_FONT);
        inventoryInfoPanel.add(inventoryInfoLabel, c);
        int TEXT_FIELD_WIDTH = 15;

        c.insets = new Insets(10, 3, 3, 3); // top, right, bottom, left;
        toolIdLabel = new JLabel("Tool ID: ");
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        inventoryInfoPanel.add(toolIdLabel, c);

        toolIdField = new JTextField(TEXT_FIELD_WIDTH);
        toolIdField.setEditable(false);
        c.gridx = 2;
        inventoryInfoPanel.add(toolIdField, c);

        toolNameLabel = new JLabel("Name / Description: ");
        c.gridx = 0;
        c.gridy = 2;
        inventoryInfoPanel.add(toolNameLabel, c);

        toolNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        inventoryInfoPanel.add(toolNameField, c);

        stockLabel = new JLabel("Stock: ");
        c.gridx = 0;
        c.gridy = 3;
        inventoryInfoPanel.add(stockLabel, c);

        stockField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        inventoryInfoPanel.add(stockField, c);

        priceLabel = new JLabel("Price: ");
        c.gridx = 0;
        c.gridy = 4;
        inventoryInfoPanel.add(priceLabel, c);

        priceField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        inventoryInfoPanel.add(priceField, c);

        supplierIdLabel = new JLabel("Supplier ID: ");
        c.gridx = 0;
        c.gridy = 5;
        inventoryInfoPanel.add(supplierIdLabel, c);

        supplierIdField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        inventoryInfoPanel.add(supplierIdField, c);

        toolTypeLabel = new JLabel("Tool Type: ");
        c.gridx = 0;
        c.gridy = 6;
        inventoryInfoPanel.add(toolTypeLabel, c);

        String[] toolType = { "Electrical", "Hand" };
        toolTypeComboBox = new JComboBox<String>(toolType);
        c.gridx = 2;
        inventoryInfoPanel.add(toolTypeComboBox, c);

        c.insets = new Insets(3, 0, 3, 0);

        addTool = new JButton("Add");
        addTool.setActionCommand("add");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        inventoryInfoPanel.add(addTool, c);

        updateButton = new JButton("Update");
        updateButton.setActionCommand("update");
        c.gridx = 1;
        c.gridy = 7;
        inventoryInfoPanel.add(updateButton, c);

        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        c.gridx = 2;
        inventoryInfoPanel.add(deleteButton, c);

        clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        c.gridx = 3;
        inventoryInfoPanel.add(clearButton, c);
    }

    private void buildInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 20, 20, 20); // top, right, bottom, left;
        c.gridx = 0;
        c.gridy = 0;
        inventoryPanel.add(searchQueryPanel, c);

        c.gridy = 1;
        inventoryPanel.add(searchResultsPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        inventoryPanel.add(inventoryInfoPanel, c);

        c.gridx = 1;
        c.gridy = 1;
        inventoryPanel.add(posPanel, c);
    }

    public void registerButtons(ActionListener listener) {
        registerListener(listener, searchButton);
        registerListener(listener, clearSearchButton);
        registerListener(listener, updateButton);
        registerListener(listener, deleteButton);
        registerListener(listener, clearButton);
        registerListener(listener, searchAllButton);
        registerListener(listener, addTool);

    }

    public void registerComboBox(ActionListener listener) {
        registerListener(listener, idButton);
        registerListener(listener, nameButton);
        registerListener(listener, typeButton);

    }

    private void registerListener(ActionListener listener, AbstractButton button) {
        button.addActionListener(listener);
    }

    public void registerResultsList(ListSelectionListener listener) {
        resultsList.addListSelectionListener(listener);
    }

    public void registerGuiMenu(ActionListener menuListener) {
        super.gui.registerInventoryButton(menuListener);
    }

    public void clearInfoFields() {
        for (String key : fields.keySet()) {
            fields.get(key).setText("");
        }
    }

    public void clearSearch() {
        searchQueryField.setText("");
        listModel.clear();
    }

    public void generateTestList() {
        listModel.addElement("Test1");
        listModel.addElement("Test2");
    }

    public String getSearchField() {
        return searchQueryField.getText();
    }

    public HashMap<String, String> getInfoFields() {
        HashMap<String, String> response = new HashMap<String, String>();

        for (String key : fields.keySet()) {
            response.put(key, fields.get(key).getText());
        }
        response.put("toolTypeComboBox", (String) toolTypeComboBox.getSelectedItem());
        return response;
    }

    @Override
    public void flashErrorMessage(String error) {
        JOptionPane.showMessageDialog(inventoryPanel, error, "ERROR!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void flashSuccessMessage(String success) {
        JOptionPane.showMessageDialog(inventoryPanel, success, "Success!", JOptionPane.PLAIN_MESSAGE);
    }

    @Override
    public HashMap<String, JTextField> getFields() {
        return fields;
    }

    // @Override
    // public HashMap<String, JTextField> getInfoFields() {
    // return fields;
    // }

    @Override
    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    @Override
    public JTextField getField(String fieldName) {
        for (String key : fields.keySet()) {
            if (key.equals(fieldName)) {
                return fields.get(key);
            }
        }
        return null;
    }

    public JComboBox getComboBox() {
        return this.toolTypeComboBox;
    }
}
