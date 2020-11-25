package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Electrical extends Tool {

	private String powerType;
	
	public Electrical(int toolID, String name, String type, int qty, double price, int supplierID, String powerType) {
		super(toolID, name, type, qty, price, supplierID);
		this.powerType = powerType;
	}

	@Override
	public JSONObject encode() {
		JSONObject obj = super.encode();
		try {
			obj.put("PowerType", powerType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
