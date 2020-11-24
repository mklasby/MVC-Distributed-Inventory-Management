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
    public JRadioButton toolIdButton;
    public JRadioButton toolNameButton;
    public JRadioButton toolTypeButton;
    public ButtonGroup buttonGroup;
    public JTextField searchQueryField;
    public JTextField toolIdField;
    public JTextField toolNameField;
    public JTextField stockField;
    public JTextField priceField;
    public JTextField supplierIdField;
    public JTextField toolTypeField;
    public JComboBox<String> toolTypeComboBox;
    public JButton searchButton;
    public JButton clearSearchButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton clearButton;
    public JButton sellTool;
    public JButton returnTool;
    public JButton generateOrder;
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
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        posPanel.add(posLabel, c);

        sellTool = new JButton("Sell Tool");
        sellTool.setActionCommand("sellTool");
        c.insets = new Insets(3, 0, 3, 0); // top, right, bottom, left;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        posPanel.add(sellTool, c);

        returnTool = new JButton("Return Tool");
        returnTool.setActionCommand("returnTool");
        c.gridy = 2;
        posPanel.add(returnTool, c);

        generateOrder = new JButton("Generate Order");
        generateOrder.setActionCommand("generateOrder");
        c.gridy = 3;
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
    }

    public String getFieldText(String fieldName) {
        return fields.get(fieldName).getText();
    }

    // TODO: Ask Moshi about refactoring GUIs
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
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        searchQueryPanel.add(searchInvLabel, c);

        searchTypeLabel = new JLabel("Select type of search to be performed:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        searchQueryPanel.add(searchTypeLabel, c);

        toolIdButton = new JRadioButton("Tool ID");
        toolIdButton.setSelected(true);
        toolIdButton.setActionCommand("toolId");
        toolNameButton = new JRadioButton("Tool Name / Description");
        toolNameButton.setActionCommand("toolName");
        toolTypeButton = new JRadioButton("Tool Type");
        toolTypeButton.setActionCommand("toolType");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(toolIdButton);
        buttonGroup.add(toolNameButton);
        buttonGroup.add(toolTypeButton);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        searchQueryPanel.add(toolIdButton, c);
        c.gridy = 3;
        searchQueryPanel.add(toolNameButton, c);
        c.gridy = 4;
        searchQueryPanel.add(toolTypeButton, c);

        c.insets = new Insets(10, 3, 0, 3); // top, right, bottom, left;
        searchQueryLabel = new JLabel("Enter Search Parameter below:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 5;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        searchQueryPanel.add(searchQueryLabel, c);

        c.insets = new Insets(0, 3, 0, 3); // top, right, bottom, left;
        searchQueryField = new JTextField(15);
        searchQueryField.setEditable(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
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

        c.insets = new Insets(0, 0, 10, 3); // top, right, bottom, left;
        clearSearchButton = new JButton("Clear Search");
        clearSearchButton.setActionCommand("clearSearch");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
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
        c.gridwidth = 6;
        inventoryInfoLabel = new JLabel("Tool Information:");
        inventoryInfoLabel.setFont(ViewConstants.SUBTITLE_FONT);
        inventoryInfoPanel.add(inventoryInfoLabel, c);
        int TEXT_FIELD_WIDTH = 15;

        c.insets = new Insets(10, 3, 3, 3); // top, right, bottom, left;
        toolIdLabel = new JLabel("Tool ID: ");
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 3;
        inventoryInfoPanel.add(toolIdLabel, c);

        toolIdField = new JTextField(TEXT_FIELD_WIDTH);
        // toolIdField.setEditable(false);
        c.gridx = 3;
        inventoryInfoPanel.add(toolIdField, c);

        toolNameLabel = new JLabel("Name / Description: ");
        c.gridx = 0;
        c.gridy = 2;
        inventoryInfoPanel.add(toolNameLabel, c);

        toolNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        inventoryInfoPanel.add(toolNameField, c);

        stockLabel = new JLabel("Stock: ");
        c.gridx = 0;
        c.gridy = 3;
        inventoryInfoPanel.add(stockLabel, c);

        stockField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        inventoryInfoPanel.add(stockField, c);

        priceLabel = new JLabel("Price: ");
        c.gridx = 0;
        c.gridy = 4;
        inventoryInfoPanel.add(priceLabel, c);

        priceField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        inventoryInfoPanel.add(priceField, c);

        supplierIdLabel = new JLabel("Supplier ID: ");
        c.gridx = 0;
        c.gridy = 5;
        inventoryInfoPanel.add(supplierIdLabel, c);

        supplierIdField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        inventoryInfoPanel.add(supplierIdField, c);

        toolTypeLabel = new JLabel("Tool Type: ");
        c.gridx = 0;
        c.gridy = 6;
        inventoryInfoPanel.add(toolTypeLabel, c);

        // toolTypeField = new JTextField(TEXT_FIELD_WIDTH);
        // c.gridx = 3;
        // inventoryInfoPanel.add(toolTypeField, c);

        // custTypeLabel = new JLabel("Customer Type: ");
        // c.gridx = 0;
        // c.gridy = 7;
        // inventoryInfoPanel.add(custTypeLabel, c);

        String[] toolType = { "Electrical", "Hand" };
        toolTypeComboBox = new JComboBox<String>(toolType);
        c.gridx = 3;
        inventoryInfoPanel.add(toolTypeComboBox, c);

        c.insets = new Insets(3, 0, 3, 0);
        updateButton = new JButton("Update");
        updateButton.setActionCommand("update");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        inventoryInfoPanel.add(updateButton, c);

        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        c.gridx = 2;
        c.gridwidth = 2;
        inventoryInfoPanel.add(deleteButton, c);

        clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        c.gridx = 4;
        c.gridwidth = 2;
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

    }

    public void registerComboBox(ActionListener listener) {
        registerListener(listener, toolIdButton);
        registerListener(listener, toolNameButton);
        registerListener(listener, toolTypeButton);

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
    public void flashErrorMessage(String string) {
    }

    @Override
    public void flashSuccessMessage(String success) {
        JOptionPane.showMessageDialog(inventoryPanel, (Object)success, "Success!");
        gui.flashMessage()
	}
}
