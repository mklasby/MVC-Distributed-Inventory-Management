package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

public class Electrical extends Tool {

	public Electrical(int toolID, String name, String type, int qty, double price, int supplierID, String powerType) {
		super(toolID, name, type, qty, price, supplierID);
		try {
			put("PowerType", powerType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JSONObject encode() {
		return this;
	}

}
