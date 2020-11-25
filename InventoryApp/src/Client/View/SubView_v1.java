package Client.View;

import java.util.HashMap;
import javax.swing.*;

public abstract class SubView_v1 {
    public Gui gui;
    public JPanel mainPanel;
    public JPanel searchQueryPanel;
    public JPanel infoPanel;
    public JPanel resultsPanel;
    public JLabel searchLabel;
    public JLabel searchTypeLabel;
    public JLabel searchQueryLabel;
    public JLabel searchResultsLabel;
    public JLabel infoLabel;
    public JLabel idLabel;
    public JLabel nameLabel;
    public JRadioButton idButton;
    public JRadioButton nameButton;
    public JRadioButton typeButton;
    public ButtonGroup buttonGroup;
    public JTextField searchQueryField;
    public JTextField idField;
    public JTextField nameField;
    public JTextField stockField;
    public JTextField priceField;
    public JTextField supplierIdField;
    public JTextField toolTypeField;
    public JTextField saleQuantityField;
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

    SubView_v1(Gui gui) {
        this.gui = gui;
    }

    SubView_v1() {

    }

    public abstract void display();

    public abstract void flashErrorMessage(String string);

    public abstract void flashSuccessMessage(String success);

    public abstract HashMap<String, JTextField> getFields();

    public abstract DefaultListModel<String> getListModel();

    public abstract JTextField getField(String fieldName);
}
