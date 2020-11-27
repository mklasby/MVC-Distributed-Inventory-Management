package CustomerModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Residential extends Customer {

	String type; 

	public Residential(int clientId, String fName, String lName, String address, String postal, String phone,
			String postalCode, String type) {
		super(clientId, fName, lName, address, postal, phone, postalCode);
		setType(type);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    @Override
    public void putFields() {
        try {
            super.putFields();
            put("Type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public JSONObject encode() {
        super.putFields();
        putFields();
        return this;
    }
    
	public String toDescriptionString() {
		// TODO
        return "";
    }
	
}
