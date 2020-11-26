package InventoryModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import org.json.JSONArray;

/**
 * Class to control function of back end. Conducts back end modules as directed by app. Returns primitive data types to app to reduce coupling of lower level classes. 
 */

public class InvMgmt {
    private Inventory inventory;
    private Procurement procurement;

    public InvMgmt() {
        inventory = new Inventory();
        procurement = new Procurement();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory i) {
        this.inventory = i;
    }

    public Procurement getProcurement() {
        return this.procurement;
    }

    public void setProcurement(Procurement p) {
        this.procurement = p;
    }

    /**
     * Generates order from inventory.pendingOrders and clears pendingOrders once
     * order has been generated.
     * 
     * @return: String - success/fail message
     */
    public String generateOrder() {
        String result = this.procurement.generateOrder(this.inventory.getPendingOrders());
        // clear pending orders in Inventory now that we have collected and issued into
        // an order
        this.inventory.setPendingOrders(new ArrayList<OrderLine>());
        return result;
    }

    /**
     * Generates order for all tools where tool.quantity < tool.lowSupply
     * 
     * @return: String - success/fail message
     */
    public String generateOrderForAll() {
        inventory.populatePendingOrder();
        String message = this.generateOrder();
        return message;
    }

	public JSONArray encodeToolSearchQuery(ResultSet rs) {
		return inventory.encodeSearchQuery(rs);
	}

	public Object encodeCustomerSearchQuery(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
}
