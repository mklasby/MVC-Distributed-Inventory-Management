package lab3_1;

/**
 * Shop communicates with the front end (ShopApp) and keep as record the inventory of tools and
 * the supplier list.
 *   
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class Shop {

	/**
	 * tool inventory
	 */
	private Inventory theInventory;
	
	/**
	 * supplier list
	 */
	private SupplierList suppliers;
	
	/**
	 * Construct a shop object.
	 * @param i Inventory object
	 * @param s SupplierList object
	 */
	public Shop (Inventory i, SupplierList s) {
		theInventory = i;
		suppliers = s;
	}
	
	/**
	 * Print all Tool items in inventory including their id, name, and quantity.
	 */
	public void viewItems() {
		theInventory.printItems();
	}
	
	/**
	 * Search inventory for tool with given name.
	 * @param name tool name
	 * @return the Tool object with given name, or null if not found
	 */
	public Tool searchToolByName(String name) {
		return theInventory.searchToolbyName(name);
	}

	/**
	 * Search inventory for tool with given id.
	 * @param id tool id
	 * @return the Tool object with given id, or null if not found
	 */
	public Tool searchToolByID(int id) {
		return theInventory.searchToolbyID(id);
	}

	/**
	 * Check inventory and generate order line if each item that is low in stock.
	 * @param tool sold item (quantity decreased by one)
	 */
	public void manageInventory(Tool tool) {
		theInventory.manageInventory(tool);
	}

}
