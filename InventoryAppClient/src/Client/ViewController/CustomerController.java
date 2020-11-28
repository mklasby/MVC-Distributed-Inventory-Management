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
import Client.CommonModel.CommercialCustomer;
import Client.CommonModel.Customer;
import Client.CommonModel.ResidentialCustomer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerController extends ViewController {
    public HashMap<Integer, JSONObject> searchResults;
    private String searchBy = "id";
    private int selectedIdx = -1;
    private String[] infoKeys;

    public CustomerController(SubView view, ClientController clientCtrl) {
        super(clientCtrl, view);
        view.registerGuiMenuButton(new MenuListener(), "customerPanelButton");
        view.registerButtonListener(new ButtonListener());
        view.registerRadioButtonListener(new RadioButtonListener());
        view.registerListListener(new ResultsListListener());
        infoKeys = new String[] { "firstNameField", "lastNameField", "addressField", "postalField", "phoneField" };
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
        HashMap<String, JList> lists = view.getLists();
        DefaultListModel listModel = (DefaultListModel) lists.get("resultsList").getModel();
        listModel.clear();
        Message query;
        try {
            query = new Message(REQUEST, GET, CUSTOMER, ALL);
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
            String typeField = view.getField("searchQueryField").getText().toLowerCase();
            if (!typeField.equals("residential") && !typeField.equals("commercial")) {
                view.flashErrorMessage("ERROR: Please enter \"Residential\" or \"Commercial\" for Tool Type");
                return;
            }
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
            query = new Message(REQUEST, GET, CUSTOMER, search);
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
            listModel.add(key, getCustomerString((JSONObject) searchResults.get(key)));
        }
    }

    public String getCustomerString(JSONObject customerJSON) throws JSONException {
        String type = customerJSON.getString("Type");
        if (type.equals("Residential")) {
            ResidentialCustomer customer = new ResidentialCustomer(customerJSON);
            return customer.toDescriptionString();
        } else {
            CommercialCustomer customer = new CommercialCustomer(customerJSON);
            return customer.toDescriptionString();
        }
    }

    public JSONObject getCustomerJSON() throws JSONException {
        HashMap<String, JTextField> fields = getInfoFields();
        int clientId;
        try {
            if (selectedIdx == -1) {
                clientId = -1;
            } else {
                clientId = Integer.parseInt(view.getFieldText("customerIdField"));
            }
            String fName = fields.get("firstNameField").getText();
            String lName = fields.get("lastNameField").getText();
            String address = fields.get("addressField").getText();
            String postal = fields.get("postalField").getText();
            String phone = fields.get("phoneField").getText();
            String custType = view.getComboBox("custTypeComboBox").getSelectedItem().toString();
            if (custType.equals("Residential")) {
                ResidentialCustomer thisCust = new ResidentialCustomer(clientId, fName, lName, address, postal, phone);
                return thisCust.encode();
            } else {
                CommercialCustomer thisCust = new CommercialCustomer(clientId, fName, lName, address, postal, phone);
                return thisCust.encode();
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
            view.flashErrorMessage("Silly goose, select a Customer from the menu on the left before deleting!");
            return;
        }
        JSONObject delete = searchResults.get(selectedIdx);
        try {
            Message message = new Message(REQUEST, DELETE, CUSTOMER, delete);
            Message response = clientCtrl.sendMessage(message);
            if (isErrorMessage(response)) {
                return;
            }
            if (response.get(VERB).equals(OK)) {
                view.flashSuccessMessage((String) response.get(DATA));
                clearInfoFields();
                searchAll();
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
            JSONObject newItem = getCustomerJSON();
            Message put = new Message(REQUEST, PUT, CUSTOMER, newItem);
            clientCtrl.sendMessage(put);
            view.flashSuccessMessage("Customer updated successfully!");
            clearInfoFields();
            searchAll();
        } catch (JSONException e) {
            view.flashErrorMessage("Oops, I did it again...");
            e.printStackTrace();
        }
    }

    private void addEntry() {
        if (areFieldsEmpty()) {
            view.flashErrorMessage("ERROR! Please input data into all info fields");
            return;
        }
        try {
            JSONObject newItem = getCustomerJSON();
            Message post = new Message(REQUEST, POST, CUSTOMER, newItem);
            Message response = clientCtrl.sendMessage(post);
            if (isErrorMessage(response)) {
                return;
            } else {
                view.flashSuccessMessage(response.getString(DATA));
                clearInfoFields();
                searchAll();
            }
        } catch (JSONException e) {
            view.flashErrorMessage("Oops, I did it again...");
            e.printStackTrace();
        }
    }

    private void populateFields(int idxSelected) {
        JSONObject jsonObject = searchResults.get(idxSelected);
        System.out.println("POPULATING FIELDS " + jsonObject.toString());
        try {
            String type = jsonObject.getString("Type");
            JComboBox comboBox = view.getComboBox("custTypeComboBox");
            Customer customer;
            if (type.equals("Residential")) {
                comboBox.setSelectedIndex(1);
                customer = new ResidentialCustomer(jsonObject);
            } else {
                comboBox.setSelectedIndex(0);
                customer = new CommercialCustomer(jsonObject);
            }

            HashMap<String, JTextField> fields = view.getFields();
            for (String key : fields.keySet()) {
                JTextField thisField = fields.get(key);
                if (key.equals("customerIdField")) {
                    thisField.setText(Integer.toString(customer.getClientId()));
                } else if ((key.equals("firstNameField"))) {
                    thisField.setText(customer.getfName());
                } else if ((key.equals("lastNameField"))) {
                    thisField.setText(customer.getlName());
                } else if ((key.equals("addressField"))) {
                    thisField.setText(customer.getAddress());
                } else if ((key.equals("phoneField"))) {
                    thisField.setText(customer.getPhone());
                } else if (key.equals("postalField")) {
                    thisField.setText(customer.getPostalCode());
                }
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
                String idText = view.getFieldText("customerIdField");
                deleteRecord(idText);
            } else if (cmd == "clear") {
                clearInfoFields();
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
