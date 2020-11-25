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
import Client.CommonModel.*;

public class InventoryController extends ViewController implements ClientServerConstants {
    public InventoryView view;
    public HashMap<Integer, JSONObject> searchResults;
    private String searchBy = "id";
    private int selectedIdx = 0;

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

    private String getQueryType() {
        String queryType = null;
        if (searchBy.equals("id")) {
            queryType = BY_ID;
        } else if (searchBy.equals("name")) {
            queryType = BY_NAME;
        } else {
            queryType = BY_TYPE;
        }
        return queryType;
    }

    public void searchAll() {
        Message query;
        try {
            query = new Message(REQUEST, GET, INVENTORY, ALL);
            Message response = clientCtrl.sendMessage(query);
            populateSearchResults(response);
        } catch (JSONException e) {
            view.flashErrorMessage("...got lost in the game...");
            e.printStackTrace();
        }
    }

    public void search() {
        String search = view.getField("searchQueryField").getText();
        if (search.equals("") | search.equals(" ")) {
            searchAll();
        }
        Message query = null;
        if (searchBy.equals("type")) {
            String typeField = view.getField("searchQueryField").getText().toLowerCase();
            if (!typeField.equals("hand") && !typeField.equals("electrical")) {
                view.flashErrorMessage("ERROR: Please enter \"Hand\" or \"Electrical\" for Tool Type");
                return;
            }
        }
        try {
            query = new Message(REQUEST, GET, INVENTORY, search);
            query.addQueryType(getQueryType());
        } catch (JSONException e) {
            view.flashErrorMessage("ERROR: We were unable to process your request, please try again.");
            e.printStackTrace();
        }
        System.out.println("MESSAGE RECEIVED: " + query.toString());

        Message response = null;
        try {
            response = clientCtrl.sendMessage(query);
            if (response.get(VERB).equals(ERROR)) {
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
            populateSearchResults(response);
        } catch (Exception e) {
            e.printStackTrace();
            view.flashErrorMessage("Something went wrong...Please try again.");
        }
        return;
    }

    private void populateSearchResults(Message response) throws JSONException {
        searchResults = new HashMap<Integer, JSONObject>();
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
        // TODO: What are we calling stock?
        int stock = jsonTool.getInt("Stock");
        Double price = jsonTool.getDouble("Price");
        int supplierID = jsonTool.getInt("SupplierID");
        String toolType = jsonTool.getString("Type");

        return String.format("ToolID: %d, Name: %14s, Stock: %4d, Price: %6.2f, SupplierID: %5d, Tool Type: %10s",
                toolId, name, stock, price, supplierID, toolType);
    }

    public JSONObject getToolJSON() throws JSONException {
        HashMap<String, String> fields = view.getInfoFields();
        try {
            int toolId = Integer.parseInt(fields.get("toolIdField"));
            String name = fields.get("toolNameField");
            int stock = Integer.parseInt(fields.get("stockField"));
            double price = Double.parseDouble(fields.get("priceField"));
            int supplierId = Integer.parseInt(fields.get("supplierIdField"));
            String toolType = fields.get("toolTypeComboBox");
            Tool tool = new Tool(toolId, name, stock, price, supplierId, toolType);
            return tool.encode();
        } catch (Exception e) {
            view.flashErrorMessage("ERROR: Bad Input! Please check input field types!");
            return null;
        }
    }

    public void clearSearch() {
        view.clearSearch();
    }

    public void deleteRecord(String fieldText) {
        if (searchResults == null) {
            view.flashErrorMessage("Silly goose, select a tool from the menu on the left before deleting!");
            return;
        }
        JSONObject delete = searchResults.get(selectedIdx);
        try {
            Message message = new Message(REQUEST, DELETE, INVENTORY, delete);
            Message response = clientCtrl.sendMessage(message);
            isErrorMessage(response); // will return if true
            if (response.get(VERB).equals(OK)) {
                view.flashSuccessMessage((String) response.get(DATA));
                return;
            } else {
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
        } catch (JSONException e) {
            view.flashErrorMessage("...I played with your heart, ...");
            e.printStackTrace();
        }

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
        try {
            JSONObject newItem = getToolJSON();
            Message put = new Message(REQUEST, PUT, INVENTORY, newItem);
            clientCtrl.sendMessage(put);
        } catch (JSONException e) {
            view.flashErrorMessage("Oops, I did it again...");
            e.printStackTrace();
        }
    }

    private void returnTool() {
        view.flashErrorMessage("All Sales Final! No Soup For You!");
    }

    private void sellTool() {
        if (searchResults == null) {
            view.flashErrorMessage("Please select a tool from the menu on the left before buying!");
            return;
        }
        JSONObject sale = searchResults.get(selectedIdx);
        int stock = -1;
        // used in the event of db failure
        int existingStock = -1;
        int quantity = 0;
        try {
            stock = sale.getInt("Stock");
            existingStock = stock;
        } catch (JSONException e1) {
            // logically impossible to throw, thanks java
            e1.printStackTrace();
            return;
        }
        try {
            quantity = Integer.parseInt(view.getFieldText("quantity"));
        } catch (Exception e) {
            view.flashErrorMessage("ERROR: Please enter a number of items to buy!");
            return;
        }
        if (quantity <= 0) {
            view.flashErrorMessage("ERROR: Buying nothing is not an option in this store!");
            return;
        }
        try {
            sale.put("stock", stock - quantity);
            Message message = new Message(REQUEST, PUT, INVENTORY, sale);
            Message response = clientCtrl.sendMessage(message);
            if (response.get(VERB).equals(OK)) {
                view.flashSuccessMessage((String) response.get(DATA));
                populateResultsList();

                return;
            } else {
                // reset sale object to original state
                sale.put("stock", existingStock);
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
        } catch (JSONException e) {
            view.flashErrorMessage("...I played with your heart, ...");
            e.printStackTrace();
        }
    }

    private void generateOrder() {
        Message message;
        Message response = null;
        try {
            message = new Message(REQUEST, "ORDER", INVENTORY);
            response = clientCtrl.sendMessage(message);
            isErrorMessage(response);
            view.flashSuccessMessage(((String) response.get(DATA)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void isErrorMessage(Message response) {
        try {
            if (response.get(VERB).equals(ERROR)) {
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addEntry() {
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
    }

    private void populateFields(int idxSelected) {
        JSONObject jsonTool = searchResults.get(idxSelected);
        try {
            int toolId = jsonTool.getInt("ToolID");
            String name = jsonTool.getString("Name");
            // TODO: What are we calling stock?
            int stock = jsonTool.getInt("stockField");
            Double price = jsonTool.getDouble("Price");
            int supplierID = jsonTool.getInt("SupplierID");
            String toolType = jsonTool.getString("Type");

            HashMap<String, JTextField> fields = view.getFields();
            for (String key : fields.keySet()) {
                JTextField thisField = fields.get(key);
                if (key.equals("toolIdField")) {
                    thisField.setText(Integer.toString(toolId));
                } else if ((key.equals("toolNameField"))) {
                    thisField.setText(name);
                } else if ((key.equals("stockField"))) {
                    thisField.setText(Integer.toString(stock));
                } else if ((key.equals("priceField"))) {
                    thisField.setText(Double.toString(price));
                } else if ((key.equals("supplierIdField"))) {
                    thisField.setText(Integer.toString(supplierID));
                }
            }
            JComboBox comboBox = view.getComboBox();
            if (toolType.equals("Electrical")) {
                comboBox.setSelectedIndex(0);
            } else {
                comboBox.setSelectedIndex(1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            System.out.print(cmd);

            if (cmd == "search") {
                search();
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
            } else if (cmd == "searchAll") {
                searchAll();
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
            selectedIdx = e.getLastIndex();
            populateFields(selectedIdx);
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
