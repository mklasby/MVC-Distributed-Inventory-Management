package Client.CommonModel;

import org.json.JSONObject;

public class Customer {

    public Customer(int clientId, String fName, String lName, String address, String postal, String phone,
            String lName2) {
    }

    public Customer(JSONObject customerJSON) {
    }

    public JSONObject encode() {
        return null;
    }

    public String prettyPrint() {
        return null;
    }

    public int getId() {
        return 0;
    }

    public String getFName() {
        return null;
    }

    public String getAddress() {
        return null;
    }

    public String getPhone() {
        return null;
    }

    public String getPostal() {
        return null;
    }

}
