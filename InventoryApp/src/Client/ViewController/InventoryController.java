package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StringContent;

import java.awt.event.*;
import Client.View.*;
import Client.ClientController.*;

import org.json.JSONException;
import org.json.JSONObject;

public class InventoryController extends ViewController implements ClientServerConstants {
    public InventoryView view;
    private String searchBy = "toolId";

    public InventoryController(InventoryView view, ClientController clientCtrl) {
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

    public void searchInventory(String search) {
        Message query = null;
        try {
            query = new Message(REQUEST, GET, INVENTORY, search);
        } catch (JSONException e) {
            view.flashMessage("ERROR: We were unable to process your request, please try again.");
            e.printStackTrace();
        }
        System.out.println(query.toString());

        // TODO: Send out to clientCtrl
        // clientCtrl.sendMessage(query);
        // JSONObject response = clientCtrl.getMessage();
        // TODO: Reset searchResultsList

        return;
    }

    public void clearSearch() {
        view.clearSearch();
    }

    public void deleteRecord(String fieldText) {
        int toolId = Integer.parseInt(fieldText);
        Message query = null;
        try {
            query = new Message(REQUEST, DELETE, INVENTORY, toolId);
        } catch (JSONException e) {
            view.flashErrorMessage("ERROR: We were unable to process your request, please try again.");
            e.printStackTrace();
        }
        System.out.println(query.toString());
        String success = String.format("Tool Id %d successfully deleted!", toolId);
        view.flashSuccessMessage(success);

        // TODO: Send out to clientCtrl
        // clientCtrl.sendMessage(query);
        // JSONObject response = clientCtrl.getMessage();
        // TODO: flash message and reset info fields
    }

    public void updateRecord() {
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
                String search = view.getSearchField();
                searchInventory(search);
            } else if (cmd == "clearSearch") {
                clearSearch();
            } else if (cmd == "update") {
                updateRecord();
            } else if (cmd == "delete") {
                String toolIdFieldText = view.getFieldText("toolIdField");
                deleteRecord(toolIdFieldText);
            } else if (cmd == "clear") {
                view.clearInfoFields();
            }
        }
    }

    public class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearSearch();
            view.clearInfoFields();
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
}
