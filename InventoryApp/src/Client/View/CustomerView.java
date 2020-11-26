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

public class CustomerView extends SubView {
    public Gui gui;
    public JPanel searchQueryPanel;
    public JPanel customerInfoPanel;
    public JPanel searchResultsPanel;
    public JLabel searchCustLabel;
    public JLabel searchTypeLabel;
    public JLabel searchQueryLabel;
    public JLabel searchResultsLabel;
    public JLabel customerInfoLabel;
    public JLabel customerIdLabel;
    public JLabel firstNameLabel;
    public JLabel lastNameLabel;
    public JLabel addressLabel;
    public JLabel postalLabel;
    public JLabel phoneLabel;
    public JLabel custTypeLabel;
    public JRadioButton idButton;
    public JRadioButton nameButton;
    public JRadioButton typeButton;
    public ButtonGroup buttonGroup;
    public JTextField searchQueryField;
    public JTextField customerIdField;
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField addressField;
    public JTextField postalField;
    public JTextField phoneField;
    public JComboBox<String> custTypeComboBox;
    public JButton searchButton;
    public JButton clearSearchButton;
    public JButton updateButton;
    public JButton deleteButton;
    public JButton clearButton;
    public JButton searchAllButton;
    public JButton addButton;
    public JList<String> resultsList;
    public DefaultListModel<String> listModel;

    public CustomerView(Gui gui, String mainPanelKey) {
        super(gui, mainPanelKey);
        buildCustomerInfoPanel();
        buildSearchQueryPanel();
        buildSearchResultsPanel();
        buildMainPanel();
        registerFields();
        registerButtons();
        registerRadioButtons();
        registerLists();
        gui.addCard(mainPanel, mainPanelKey);
    }

    private void addFields() {
        fields = new HashMap<String, JTextField>();
        this.fields.put("searchQueryField", searchQueryField);
        this.fields.put("customerIdField", customerIdField);
        this.fields.put("firstNameField", firstNameField);
        this.fields.put("lastNameField", lastNameField);
        this.fields.put("addressField", addressField);
        this.fields.put("postalField", postalField);
        this.fields.put("phoneField", phoneField);
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

        searchCustLabel = new JLabel("Search Customers");
        searchCustLabel.setFont(ViewConstants.SUBTITLE_FONT);
        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        searchQueryPanel.add(searchCustLabel, c);

        searchTypeLabel = new JLabel("Select type of search to be performed:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 3;
        searchQueryPanel.add(searchTypeLabel, c);

        idButton = new JRadioButton("Customer ID");
        idButton.setSelected(true);
        idButton.setActionCommand("customerId");
        nameButton = new JRadioButton("Last Name");
        nameButton.setActionCommand("lastName");
        typeButton = new JRadioButton("Customer Type");
        typeButton.setActionCommand("customerType");
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

    private void buildCustomerInfoPanel() {
        customerInfoPanel = new JPanel();
        customerInfoPanel.setLayout(new GridBagLayout());
        customerInfoPanel.setSize(new Dimension(ViewConstants.X_DIMENSION / 2, ViewConstants.Y_DIMENSION));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 4;
        customerInfoLabel = new JLabel("Customer Information:");
        customerInfoLabel.setFont(ViewConstants.SUBTITLE_FONT);
        customerInfoPanel.add(customerInfoLabel, c);
        int TEXT_FIELD_WIDTH = 15;

        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        customerIdLabel = new JLabel("Customer ID: ");
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        customerInfoPanel.add(customerIdLabel, c);

        customerIdField = new JTextField(TEXT_FIELD_WIDTH);
        customerIdField.setEditable(true);
        c.gridx = 2;
        customerInfoPanel.add(customerIdField, c);

        firstNameLabel = new JLabel("First Name: ");
        c.gridx = 0;
        c.gridy = 2;
        customerInfoPanel.add(firstNameLabel, c);

        firstNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        customerInfoPanel.add(firstNameField, c);

        lastNameLabel = new JLabel("Last Name: ");
        c.gridx = 0;
        c.gridy = 3;
        customerInfoPanel.add(lastNameLabel, c);

        lastNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        customerInfoPanel.add(lastNameField, c);

        addressLabel = new JLabel("Customer ID: ");
        c.gridx = 0;
        c.gridy = 4;
        customerInfoPanel.add(addressLabel, c);

        addressField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        customerInfoPanel.add(addressField, c);

        postalLabel = new JLabel("Postal Code: ");
        c.gridx = 0;
        c.gridy = 5;
        customerInfoPanel.add(postalLabel, c);

        postalField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        customerInfoPanel.add(postalField, c);

        phoneLabel = new JLabel("Phone Number: ");
        c.gridx = 0;
        c.gridy = 6;
        customerInfoPanel.add(phoneLabel, c);

        phoneField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 2;
        customerInfoPanel.add(phoneField, c);

        custTypeLabel = new JLabel("Customer Type: ");
        c.gridx = 0;
        c.gridy = 7;
        customerInfoPanel.add(custTypeLabel, c);

        String[] custTypes = { "Commercial", "Residential" };
        custTypeComboBox = new JComboBox<String>(custTypes);
        c.gridx = 2;
        customerInfoPanel.add(custTypeComboBox, c);

        c.insets = new Insets(3, 0, 3, 0);
        addButton = new JButton("Add");
        addButton.setActionCommand("add");
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        customerInfoPanel.add(addButton, c);

        updateButton = new JButton("Update");
        updateButton.setActionCommand("update");
        c.gridx = 1;
        customerInfoPanel.add(updateButton, c);

        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        c.gridx = 3;
        c.gridwidth = 1;
        customerInfoPanel.add(deleteButton, c);

        clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        c.gridx = 4;
        c.gridwidth = 1;
        customerInfoPanel.add(clearButton, c);
    }

    @Override
    protected void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3); // top, right, bottom, left;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(searchQueryPanel, c);

        c.gridy = 1;
        mainPanel.add(searchResultsPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        mainPanel.add(customerInfoPanel);
    }

    @Override
    protected void registerFields() {
        fields = new HashMap<String, JTextField>();
        fields.put("searchQueryField", searchQueryField);
        fields.put("customerIdField", customerIdField);
        fields.put("firstNameField", firstNameField);
        fields.put("lastNameField", lastNameField);
        fields.put("addressField", addressField);
        fields.put("postalField", postalField);
        fields.put("phoneField", phoneField);
    }

    @Override
    protected void registerButtons() {
        buttons = new HashMap<String, JButton>();
        buttons.put("searchButton", searchButton);
        buttons.put("clearSearchButton", clearSearchButton);
        buttons.put("updateButton", updateButton);
        buttons.put("deleteButton", deleteButton);
        buttons.put("clearButton", clearButton);
        buttons.put("searchAllButton", searchAllButton);
        buttons.put("addButton", addButton);
    }

    @Override
    protected void registerRadioButtons() {
        radioButtons = new HashMap<String, JRadioButton>();
        radioButtons.put("idButton", idButton);
        radioButtons.put("nameButton", nameButton);
        radioButtons.put("typeButton", typeButton);
    }

    @Override
    protected void registerButtonGroups() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void registerLists() {
        lists = new HashMap<String, JList>();
        lists.put("resultsList", resultsList);
    }

    @Override
    protected void registerLabels() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void registerComboBoxes() {
        comboBoxes = new HashMap<String, JComboBox>();
        comboBoxes.put("custTypeComboBox", custTypeComboBox);
    }
}