package Client.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
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
    public JLabel searchResults;
    public JLabel clientInfoLabel;
    public JLabel clientIdLabel;
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
    public JTextField clientIdField;
    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField addressField;
    public JTextField postalField;
    public JTextField phoneField;
    public JTextField custTypeField;
    public JButton searchButton;
    public JButton clearSearchButton;
    public JButton saveButton;
    public JButton deleteButton;
    public JButton clearButton;

    public CustomerView() {
        super();
        this.buildGui();
    }

    public CustomerView(Gui gui) {
        super(gui);
        this.buildGui();
    }

    private void buildGui() {
        buildSearchQueryPanel();
        buildSearchResultsPanel();
        buildCustomerInfoPanel();
        buildCustomerPanel();

    }

    private void buildSearchQueryPanel() {
        customerPanel = new JPanel();
        customerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

    }

    private void buildSearchResultsPanel() {
    }

    private void buildCustomerInfoPanel() {
    }

    private void buildCustomerPanel() {
    }

    @Override
    public void display() {
        gui.setPanel("CustomerPanel");

    }

}
