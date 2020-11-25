package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StringContent;

import java.awt.event.*;
import java.util.HashMap;

import Client.View.*;
import Client.ClientController.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class InventoryController extends ViewController implements ClientServerConstants {
    public InventoryView view;
    public HashMap<Integer, JSONObject> searchResults;
    private String searchBy = "id";

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

    public void search(String search) {
        Message query = null;
        String queryType;
        if (searchBy.equals("id")) {
            queryType = BY_ID;
        } else if (searchBy.equals("name")) {
            queryType = BY_NAME;
        } else {
            queryType = BY_TYPE;
            String typeField = view.getField("searchQueryField").getText().toLowerCase();
            if (!typeField.equals("hand") && !typeField.equals("electrical")) {
                view.flashErrorMessage("ERROR: Please enter \"Hand\" or \"Electrical\" for Tool Type");
                return;
            }
        }
        try {
            query = new Message(REQUEST, GET, INVENTORY, search);
            query.addQueryType(queryType);
        } catch (JSONException e) {
            view.flashErrorMessage("ERROR: We were unable to process your request, please try again.");
            e.printStackTrace();
        }
        System.out.println(query.toString());

        Message response = null;
        try {
            response = clientCtrl.sendMessage(query);
            if (response.get(VERB).equals(ERROR)) {
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchResults = new HashMap<Integer, JSONObject>();
        try {
            populateSearchResults(response);
        } catch (JSONException e) {
            view.flashErrorMessage("Something went wrong...Please try again.");
            return;
        }
        return;
    }

    private void populateSearchResults(Message response) throws JSONException {
        JSONArray data = new JSONArray(response.get(DATA));
        for (int i = 0; i < data.length(); i++) {
            searchResults.put(i, (JSONObject) data.get(i));
        }
        populateResultsList();
    }

    private void populateResultsList() throws JSONException {
        DefaultListModel<String> listModel = view.getListModel();
        for (int key : searchResults.keySet()) {
            listModel.add(key, getToolString(searchResults.get(key)));
        }
    }

    public String getToolString(JSONObject jsonTool) throws JSONException {
        int toolId = jsonTool.getInt("ToolID");
        String name = jsonTool.getString("Name");
        int quantity = jsonTool.getInt("Quantity");
        Double price = jsonTool.getDouble("Price");
        int supplierID = jsonTool.getInt("SupplierID");
        String toolType = jsonTool.getString("Type");

        return String.format("ToolID: %d, Name: %14s, Stock: %4d, Price: %6.2f, SupplierID: %5d, Tool Type: %10s",
                toolId, name, quantity, price, supplierID, toolType);
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
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
    }

    private void returnTool() {
    }

    private void sellTool() {
    }

    private void generateOrder() {
    }

    private void addEntry() {
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            System.out.print(cmd);

            if (cmd == "search") {
                String search = view.getField("searchQueryField").getText();
                search(search);
            } else if (cmd == "clearSearch") {
                clearSearch();
            } else if (cmd == "update") {
                updateRecord();
            } else if (cmd == "delete") {
                String toolIdFieldText = view.getFieldText("toolIdField");
                deleteRecord(toolIdFieldText);
            } else if (cmd == "clear") {
                view.clearInfoFields();
            } else if (cmd == "sellTool") {
                sellTool();
            } else if (cmd == "returnTool") {
                returnTool();
            } else if (cmd == "generateOrder") {
                generateOrder();
            } else if (cmd == "addTool") {
                addEntry();
            }
        }

    }

    public class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.display();
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

    @Override
    protected boolean areFieldsEmpty() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            if (fields.get(key).getText().equals("") | fields.get(key).getText().equals(" ")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void clearInfoFields() {
        // TODO Auto-generated method stub

    }
}
