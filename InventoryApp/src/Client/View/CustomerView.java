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
    public JPanel customerPanel;
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
    public JRadioButton customerIdButton;
    public JRadioButton lastNameButton;
    public JRadioButton customerTypeButton;
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
    public JList<String> resultsList;
    public DefaultListModel<String> listModel;
    public HashMap<String, JTextField> fields;

    public CustomerView() {
        super();
        this.buildGui();
    }

    public CustomerView(Gui gui) {
        super(gui);
        this.buildGui();
        gui.addCard(customerPanel, "customerPanel");
    }

    private void buildGui() {
        buildSearchQueryPanel();
        buildSearchResultsPanel();
        buildCustomerInfoPanel();
        buildCustomerPanel();
        addFields();
        generateTestList();

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

    // TODO: Ask Moshi about refactoring GUIs
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
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        searchQueryPanel.add(searchCustLabel, c);

        searchTypeLabel = new JLabel("Select type of search to be performed:");
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        searchQueryPanel.add(searchTypeLabel, c);

        customerIdButton = new JRadioButton("Customer ID");
        customerIdButton.setSelected(true);
        customerIdButton.setActionCommand("customerId");
        lastNameButton = new JRadioButton("Last Name");
        lastNameButton.setActionCommand("lastName");
        customerTypeButton = new JRadioButton("Customer Type");
        customerTypeButton.setActionCommand("customerType");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(customerIdButton);
        buttonGroup.add(lastNameButton);
        buttonGroup.add(customerTypeButton);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.gridwidth = 2;
        searchQueryPanel.add(customerIdButton, c);
        c.gridy = 3;
        searchQueryPanel.add(lastNameButton, c);
        c.gridy = 4;
        searchQueryPanel.add(customerTypeButton, c);

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
        c.gridwidth = 6;
        customerInfoLabel = new JLabel("Customer Information:");
        customerInfoLabel.setFont(ViewConstants.SUBTITLE_FONT);
        customerInfoPanel.add(customerInfoLabel, c);
        int TEXT_FIELD_WIDTH = 15;

        c.insets = new Insets(10, 3, 3, 3); // top, right, bottom, left;
        customerIdLabel = new JLabel("Customer ID: ");
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 3;
        customerInfoPanel.add(customerIdLabel, c);

        customerIdField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(customerIdField, c);

        firstNameLabel = new JLabel("First Name: ");
        c.gridx = 0;
        c.gridy = 2;
        customerInfoPanel.add(firstNameLabel, c);

        firstNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(firstNameField, c);

        lastNameLabel = new JLabel("Last Name: ");
        c.gridx = 0;
        c.gridy = 3;
        customerInfoPanel.add(lastNameLabel, c);

        lastNameField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(lastNameField, c);

        addressLabel = new JLabel("Customer ID: ");
        c.gridx = 0;
        c.gridy = 4;
        customerInfoPanel.add(addressLabel, c);

        addressField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(addressField, c);

        postalLabel = new JLabel("Postal Code: ");
        c.gridx = 0;
        c.gridy = 5;
        customerInfoPanel.add(postalLabel, c);

        postalField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(postalField, c);

        phoneLabel = new JLabel("Phone Number: ");
        c.gridx = 0;
        c.gridy = 6;
        customerInfoPanel.add(phoneLabel, c);

        phoneField = new JTextField(TEXT_FIELD_WIDTH);
        c.gridx = 3;
        customerInfoPanel.add(phoneField, c);

        custTypeLabel = new JLabel("Customer Type: ");
        c.gridx = 0;
        c.gridy = 7;
        customerInfoPanel.add(custTypeLabel, c);

        String[] custTypes = { "Commercial", "Residential" };
        custTypeComboBox = new JComboBox<String>(custTypes);
        c.gridx = 3;
        customerInfoPanel.add(custTypeComboBox, c);

        updateButton = new JButton("Update");
        updateButton.setActionCommand("update");
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        customerInfoPanel.add(updateButton, c);

        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        c.gridx = 2;
        c.gridwidth = 2;
        customerInfoPanel.add(deleteButton, c);

        clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        c.gridx = 4;
        c.gridwidth = 2;
        customerInfoPanel.add(clearButton, c);
    }

    private void buildCustomerPanel() {
        customerPanel = new JPanel();
        customerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 20, 20, 20); // top, right, bottom, left;
        c.gridx = 0;
        c.gridy = 0;
        customerPanel.add(searchQueryPanel, c);

        c.gridy = 1;
        customerPanel.add(searchResultsPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 2;
        customerPanel.add(customerInfoPanel);
    }

    @Override
    public void display() {
        gui.setPanel("customerPanel");
    }

    public void registerButtons(ActionListener listener) {
        registerListener(listener, searchButton);
        registerListener(listener, clearSearchButton);
        registerListener(listener, updateButton);
        registerListener(listener, deleteButton);
        registerListener(listener, clearButton);

    }

    public void registerComboBox(ActionListener listener) {
        registerListener(listener, customerIdButton);
        registerListener(listener, lastNameButton);
        registerListener(listener, customerTypeButton);

    }

    private void registerListener(ActionListener listener, AbstractButton button) {
        button.addActionListener(listener);
    }

    public void registerResultsList(ListSelectionListener listener) {
        resultsList.addListSelectionListener(listener);
    }

    public void registerGuiMenu(ActionListener menuListener) {
        super.gui.registercustomerButton(menuListener);
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
}
