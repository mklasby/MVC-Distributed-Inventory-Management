package Client.CommonModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer extends JSONObject {

	int clientId;
	String lName, fName, phone, address, postalCode;

	public Customer(int clientId, String fName, String lName, String address, String postal, String phone) {
		this.clientId = clientId;
		this.fName = fName;
		this.lName = lName;
		this.phone = phone;
		this.address = address;
		this.postalCode = postal;
	}

	public Customer() {
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
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
		return String.format(
				"ClientID: %4d, Last Name: %10s, First Name: %10s, Phone: %20s, Address: %50s, Postal Code: %10s\n",
				clientId, lName, fName, phone, address, postalCode);
	}

	public void putFields() {
		try {
			this.put("ClientID", clientId);
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
