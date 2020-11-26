package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represent a tool item and keeps track of its id, name, quantity in stock,
 * price, and supplier id.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Nov 26, 2020
 */
public class Tool extends JSONObject {

	/**
	 * Construct a Tool object with the specified information
	 * 
	 * @param id       tool id
	 * @param name     description or name of tool
	 * @param tool     type (electrical or non-electrical)
	 * @param qty      quantity in stock
	 * @param price    unit price
	 * @param supplier Supplier object
	 */
	public Tool(int id, String name, String type, int qty, double price, int supplier) {
		try {
			put("ToolID", id);
			put("Name", name);
			put("Type", type);
			put("Quantity", qty);
			put("Price", price);
			put("SupplierID", supplier);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * must be overridden in children
	 */
	public Tool(JSONObject tool) {
		super();
		try{
			id = tool.get("ToolID");
			name = tool.get("Name");
			qty = tool.get("Quantity");
			price = tool.get("Price");
			supplierId = tool.get("SupplierID");
		} catch (JSONException e){
			e.printStackTrace();
		}
	}

	public JSONObject encode() {
		return this;
	}
}
