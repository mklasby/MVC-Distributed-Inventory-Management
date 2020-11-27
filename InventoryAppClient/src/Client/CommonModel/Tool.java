package Client.CommonModel;

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
	 * tool id
	 */
	protected int toolId;

	/**
	 * description or name of tool
	 */
	protected String name;

	/**
	 * tool quantity in stock
	 */
	protected int qty;

	/**
	 * tool price
	 */
	protected double price;

	/**
	 * supplier
	 */
	protected int supplierID;

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
	public Tool(int id, String name, int qty, double price, int supplier) {
		setToolId(id);
		setName(name);
		setQty(qty);
		setPrice(price);
		setSupplierID(supplier);
	}

	public Tool(JSONObject jsonTool) throws JSONException {
		toolId = jsonTool.getInt("ToolID");
		name = jsonTool.getString("Name");
		qty = jsonTool.getInt("Quantity");
		price = jsonTool.getDouble("Price");
		supplierID = jsonTool.getInt("SupplierID");
	}

	public Tool() {
	}

	public JSONObject encode() {
		this.putFields();
		return this;
	}

	public String toDescriptionString() {
		return String.format("ToolID: %d, Name: %-14s, Stock: %-4d, Price: %-6.2f, SupplierID: %-5d", toolId, name, qty,
				price, supplierID);
	}

	public void putFields() {
		try {
			put("ToolID", toolId);
			put("Name", name);
			put("Quantity", qty);
			put("Price", price);
			put("SupplierID", supplierID);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

}
