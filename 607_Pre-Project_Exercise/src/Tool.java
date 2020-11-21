/**
 * Represent a tool item and keeps track of its id, name, quantity in stock,
 * price, and supplier id.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since Nov 21, 2020
 */
public class Tool {

	/**
	 * tool id
	 */
	private int id;
		
	/** 
	 * description or name of tool
	 */
	private String name;
	
	/**
	 * tool quantity in stock
	 */
	private int qty;
	
	/**
	 * tool price
	 */
	private double price;
	
	/**
	 * supplier
	 */
	private int supplierID;
	
	/**
	 * if an order line have been generated for the tool
	 */
	private boolean ordered;
	
	/**
	 * Construct a Tool object with specified information
	 * @param id tool id
	 * @param name description or name of tool
	 * @param qty quantity in stock
	 * @param price unit price
	 * @param supplierID supplier id
	 */
	public Tool (int id, String name, int qty, double price, int supplierID) {
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.supplierID = supplierID;
		ordered = false;
	}
	
	/**
	 * Return information about the tool including its id, name, and quantity.
	 */
	@Override
	public String toString() {
		String str = String.format("Item id: %d, Item Name: %s, Item Quantity: %d", 
				id, name, qty);
		return str;
	}

	/**
	 * Return the name of this tool.
	 * @return tool name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the tool id.
	 * @return tool id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Return the tool quantity.
	 * @return tool quantity
	 */
	public int getQuantity() {
		return qty;
	}

	/**
	 * Return the supplier id.
	 * @return supplier id
	 */
	public int getSupplierID() {
		return supplierID;
	}
	
	/**
	 * Return unit price for tool item.
	 * @return tool price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Return true if quantity is successfully reduced by one, and false otherwise.
	 * @return true if quantity was updated and false otherwise
	 */
	public boolean reduceItemQty() {
		if (qty > 0) {
			qty--;
			return true;
		}
		return false;
	}
	
	/**
	 * Add order line for this tool.
	 */
	public void addOrder() {
		ordered = true;
	}
	
	/**
	 * Return if an order line have been generated for this item.
	 * @return true if an order line has been generated, and false otherwise
	 */
	public boolean isOrdered() {
		return ordered;
	}



}
