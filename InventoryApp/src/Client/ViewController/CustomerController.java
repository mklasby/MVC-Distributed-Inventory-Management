package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.HashMap;

import Client.View.*;
import Client.ClientController.*;
import org.json.JSONObject;

public class CustomerController extends ViewController {
    public CustomerView view;
    private String searchBy = "customerId";

    public CustomerController(CustomerView view, ClientController clientCtrl) {
        super(clientCtrl);
        this.view = view;
        registerGuiMenu();
        registerButtons();
        registerComboBox();
        registerResultsList();
    }

    private void registerResultsList() {
        view.registerResultsList(new ResultsListListener());
    }

    private void registerComboBox() {
        view.registerComboBox(new ComboBoxListener());
    }

    private void registerButtons() {
        view.registerButtons(new ButtonListener());
    }

    @Override
    protected void registerGuiMenu() {
        this.view.registerGuiMenu(new MenuListener());
    }

    public void searchCustomer(ActionEvent e) {
        return;
    }

    public void clearSearch(ActionEvent e) {
        clearSearch();
    }

    public void deleteRecord(ActionEvent e) {
    }

    public void updateRecord(ActionEvent e) {
    }

    public class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.display();
        }
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            System.out.print(cmd);

            if (cmd == "search") {
                searchCustomer(e);
            } else if (cmd == "clearSearch") {
                System.out.println("clear search!");
                clearSearch(e);
            } else if (cmd == "update") {
                updateRecord(e);
            } else if (cmd == "delete") {
                deleteRecord(e);
            } else if (cmd == "clear") {
                clearInfoFields();
            }
        }
    }

    public class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clearSearch();
            clearInfoFields();
            searchBy = e.getActionCommand();
        }
    }

    public class ResultsListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            System.out.print("List Selection Event\n");

            JList list = (JList) e.getSource();
            String selected = (String) list.getSelectedValue();
            populateFields(selected);
        }

        private void populateFields(String selected) {
            System.out.print("Populate fields\n");
        }
    }

    @Override
    protected boolean areFieldsEmpty() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            if (fields.get(key).getText() == "" | fields.get(key).getText() == " ") {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clearInfoFields() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            fields.get(key).setText("");
        }
    }

    @Override
    public void clearSearch() {
        JTextField searchQueryField = view.getField("searchQueryField");
        searchQueryField.setText("");
        DefaultListModel<String> listModel = view.getListModel();
        listModel.clear();
    }
}
