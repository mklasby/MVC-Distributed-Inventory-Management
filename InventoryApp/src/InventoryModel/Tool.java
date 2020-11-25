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
public class Tool {
	/** tool id */
	private int id;
		
	/** description or name of tool */
	private String name;
	
	/** description or name of tool */
	private String type;
	
	/** tool quantity in stock */
	private int qty;
	
	/** tool price */
	private double price;
	
	/** supplier id */
	private int supplierID;
	
	/**
	 * Construct a Tool object with the specified information
	 * @param id tool id
	 * @param name description or name of tool
	 * @param tool type (electrical or non-electrical)
	 * @param qty quantity in stock
	 * @param price unit price
	 * @param supplier Supplier object
	 */
	public Tool (int id, String name, String type, int qty, double price, int supplier) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.qty = qty;
		this.price = price;
		this.supplierID = supplier;
	}

	public JSONObject encode() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("ToolID", id);		
			obj.put("Name", name);
			obj.put("Type", type);
			obj.put("Quantity",  qty);
			obj.put("Price", price);
			obj.put("SupplierID", supplierID);
			return obj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
