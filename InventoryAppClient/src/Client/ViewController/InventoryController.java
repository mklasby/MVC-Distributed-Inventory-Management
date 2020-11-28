package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StringContent;

import java.awt.event.*;
import java.security.Key;
import java.util.HashMap;

import Client.View.*;
import Client.ClientController.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import Client.CommonModel.*;

public class InventoryController extends ViewController {
    public HashMap<Integer, JSONObject> searchResults;
    private String searchBy = "id";
    private int selectedIdx = 0;
    private String[] infoKeys;

    public InventoryController(SubView view, ClientController clientCtrl) {
        super(clientCtrl, view);
        view.registerGuiMenuButton(new MenuListener(), "inventoryPanelButton");
        view.registerButtonListener(new ButtonListener());
        view.registerRadioButtonListener(new RadioButtonListener());
        view.registerListListener(new ResultsListListener());
        view.registerWindowClosingListener(new MyWindowStateListener());
        infoKeys = new String[] { "toolIdField", "toolNameField", "stockField", "priceField", "supplierIdField",
                "quantityField" };
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
        searchResults = new HashMap<Integer, JSONObject>();
        HashMap<String, JList> lists = view.getLists();
        DefaultListModel listModel = (DefaultListModel) lists.get("resultsList").getModel();
        listModel.clear();
        Message query;
        try {
            query = new Message(REQUEST, GET, INVENTORY, ALL);
            query.addQueryType(ALL);
            Message response = clientCtrl.sendMessage(query);
            if (isErrorMessage(response)) {
                return;
            }
            populateSearchResults(response);
        } catch (JSONException e) {
            view.flashErrorMessage("...got lost in the game...");
            e.printStackTrace();
        }
    }

    public void search() {
        searchResults = new HashMap<Integer, JSONObject>();
        HashMap<String, JList> lists = view.getLists();
        DefaultListModel listModel = (DefaultListModel) lists.get("resultsList").getModel();
        listModel.clear();
        String search = view.getField("searchQueryField").getText();
        if (search.equals("") | search.equals(" ")) {
            searchAll();
            return;
        }
        Message query = null;
        if (searchBy.equals("type")) {
            if (!search.equalsIgnoreCase("hand") && !search.equalsIgnoreCase("electrical")) {
                view.flashErrorMessage("ERROR: Please enter \"Hand\" or \"Electrical\" for Tool Type");
                return;
            }
            if (search.equalsIgnoreCase("hand"))
                search = "non-electrical";

        }

        if (searchBy.equals("id")) {
            try {
                int nameField = Integer.parseInt(search);
            } catch (NumberFormatException e) {
                view.flashErrorMessage("Please enter id as an Integer.");
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
        JSONArray data = (JSONArray) response.get(DATA);
        for (int i = 0; i < data.length(); i++) {
            searchResults.put(i, (JSONObject) data.get(i));
        }
        populateResultsList();
    }

    private void populateResultsList() throws JSONException {
        HashMap<String, JList> lists = view.getLists();
        DefaultListModel listModel = (DefaultListModel) lists.get("resultsList").getModel();
        for (int key : searchResults.keySet()) {
            listModel.add(key, getToolString(searchResults.get(key)));
        }
    }

    public String getToolString(JSONObject jsonTool) throws JSONException {
        String toolType = jsonTool.getString("Type");
        if (toolType.equals("Electrical")) {
            Electrical thisTool = new Electrical(jsonTool);
            return thisTool.toDescriptionString();
        } else {
            NonElectrical thisTool = new NonElectrical(jsonTool);
            return thisTool.toDescriptionString();
        }
    }

    public JSONObject getToolJSON() throws JSONException {
        HashMap<String, JTextField> fields = getInfoFields();
        try {
            int toolId = Integer.parseInt(fields.get("toolIdField").getText());
            String name = fields.get("toolNameField").getText();
            int quantity = Integer.parseInt(fields.get("stockField").getText());
            double price = Double.parseDouble(fields.get("priceField").getText());
            int supplierId = Integer.parseInt(fields.get("supplierIdField").getText());
            String toolType = view.getComboBox("toolTypeComboBox").getSelectedItem().toString();
            if (toolType.equals("Electrical")) {
                String powerType = "Type A";
                Electrical thisTool = new Electrical(toolId, name, quantity, price, supplierId, powerType);
                return thisTool.encode();
            } else {
                NonElectrical thisTool = new NonElectrical(toolId, name, quantity, price, supplierId);
                return thisTool.encode();
            }
        } catch (Exception e) {
            view.flashErrorMessage("ERROR: Bad Input! Please check input field types!");
            return null;
        }
    }

    public void clearSearch() {
        view.getField("searchQueryField").setText("");
        HashMap<String, JList> lists = view.getLists();
        DefaultListModel listModel = (DefaultListModel) lists.get("resultsList").getModel();
        listModel.clear();
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
            if (isErrorMessage(response)) {
                return;
            }
            if (response.get(VERB).equals(OK)) {
                view.flashSuccessMessage((String) response.get(DATA));
                return;
            }
        } catch (JSONException e) {
            view.flashErrorMessage("...I played with your heart, ...");
            e.printStackTrace();
        }
    }

    public void updateRecord() {
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
        try {
            JSONObject newItem = getToolJSON();
            Message put = new Message(REQUEST, PUT, INVENTORY, newItem);
            Message response = clientCtrl.sendMessage(put);
            if (isErrorMessage(response)) {
                return;
            } else {
                view.flashSuccessMessage(response.getString(DATA));
                searchAll();
            }
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
            stock = sale.getInt("Quantity");
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
            sale.put("Quantity", stock - quantity);
            Message message = new Message(REQUEST, SALE, INVENTORY, sale);
            Message response = clientCtrl.sendMessage(message);
            if (isErrorMessage(response)) {
                // reset sale object to original state
                sale.put("Quantity", existingStock);
                view.flashErrorMessage((String) response.get(DATA));
                return;
            } else {
                view.flashSuccessMessage("Tool sold to one lucky customer!");
                searchAll();
                clearInfoFields();
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
            message = new Message(REQUEST, "ORDER", INVENTORY, "");
            response = clientCtrl.sendMessage(message);
            if (isErrorMessage(response)) {
                return;
            }
            System.out.println(response);

            view.flashSuccessMessage(OrderWriter.writeOrder(response));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addEntry() {
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
        try {
            JSONObject newItem = getToolJSON();
            Message post = new Message(REQUEST, POST, INVENTORY, newItem);
            Message response = clientCtrl.sendMessage(post);
            if (isErrorMessage(response)) {
                return;
            } else {
                view.flashSuccessMessage(response.getString(DATA));
                searchAll();
            }
        } catch (JSONException e) {
            view.flashErrorMessage("Oops, I did it again...");
            e.printStackTrace();
        }
    }

    private void populateFields(int idxSelected) {
        JSONObject jsonTool = searchResults.get(idxSelected);
        System.out.println("POPULATING FIELDS " + jsonTool.toString());
        try {
            int toolId = jsonTool.getInt("ToolID");
            String name = jsonTool.getString("Name");
            int stock = jsonTool.getInt("Quantity");
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
            JComboBox comboBox = view.getComboBox("toolTypeComboBox");
            System.out.print(comboBox.getSelectedItem());

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
                clearInfoFields();
            } else if (cmd == "sellTool") {
                sellTool();
            } else if (cmd == "returnTool") {
                returnTool();
            } else if (cmd == "generateOrder") {
                generateOrder();
            } else if (cmd == "add") {
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

    public class RadioButtonListener implements ActionListener {

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
            JList list = (JList) e.getSource();
            selectedIdx = list.getSelectedIndex();
            System.out.printf("LIST SELECTION: Index %d\n", selectedIdx);
            if (selectedIdx == -1) {
                return;
            }
            populateFields(selectedIdx);
        }

    }

    @Override
    protected boolean areFieldsEmpty() {
        HashMap<String, JTextField> fields = getInfoFields();
        for (String key : fields.keySet()) {
            if (fields.get(key).getText().equals("") | fields.get(key).getText().equals(" ")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void clearInfoFields() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            fields.get(key).setText("");
        }

    }

    @Override
    protected boolean areInfoFieldsEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void registerListeners() {
        // TODO Auto-generated method stub

    }

    @Override
    protected HashMap<String, JTextField> getInfoFields() {
        HashMap<String, JTextField> infoFields = new HashMap<String, JTextField>();
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            for (String info : infoKeys) {
                if (info.equals(key)) {
                    infoFields.put(key, fields.get(key));
                }
            }
        }
        return infoFields;
    }
}
