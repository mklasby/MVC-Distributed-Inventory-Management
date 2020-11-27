package CustomerModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer extends JSONObject {

	int clientID;
	String lName, fName, phone, address, postalCode;
	  
    public Customer(int clientId, String fName, String lName, String address, String postal, String phone,
            String postalCode) {
    	clientID = clientId;
    	this.fName = fName;
    	this.lName = lName;
    	this.phone = phone;
    	this.address = address;
    	postalCode = postal;
    }
    
    public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String toDescriptionString() {
        return String.format("ClientID: %4d, Last Name: %10s, First Name: %10s, Phone: %20s, Address: %50s, Postal Code: %10s\n", 
        		clientID, lName, fName, phone, address, postalCode);
    }

    public void putFields() {
        try {
            this.put("ClientID", clientID);
            this.put("LName", lName);
            this.put("FName", fName);
            this.put("Phone", phone);
            this.put("Address", address);
            this.put("PostalCode", postalCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public JSONObject encode() {
    	putFields();
        return this;
    }



}
