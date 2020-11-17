package toolshop;

/**
 * Class to control function of back end. Conducts back end modules as directed by app. Returns primitive data types to app to reduce coupling of lower level classes. 
 */

import java.util.ArrayList;

public class InvMgmt {
    private Inventory inventory;
    private Procurement procurement;

    InvMgmt() {
        inventory = DBReader.readTools();
        procurement = DBReader.readSuppliers();
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String printInventory() {
        return inventory.toString();
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
     * Method to receive order and add received quantity to inventory. TODO:
     * Implement me
     * 
     * @param fname: String - name of order file received
     * @return: String - success/fail message
     */
    public String receiveOrder(String fname) {
        return "Please upgrade to our Premium Plan to unlock this feature!\n";
        // ArrayList<OrderLine> lines = DBReader.readOrder(fname);
        // return this.inventory.receiveOrder(lines);
    }

    /**
     * Sells 1 quantity of tool.
     * 
     * @param toolID: int - tool to sell
     * @return: String - success/fail message
     */
    public String makeSale(int toolID) {
        String result = inventory.makeSale(toolID);
        return result;
    }

    /**
     * Returns 1 quantity of tool. TODO: Implement me
     * 
     * @param toolID: int - tool to return
     * @return: String - success/fail message
     */
    public String returnTool(int toolID) {
        String result = inventory.returnTool(toolID);
        return result;
    }

    /**
     * Searches inventory by tool.description. Returns best matches in descending
     * order
     * 
     * @param name: String - tool.description to search for
     * @return: String - Search results
     */
    public String searchInventory(String name) {
        String result = inventory.searchInventory(name);
        return result;
    }

    /**
     * Searches inventory by tool.toolID. Returns exact matches only
     * 
     * @param toolID: int - toolID to search for
     * @return: String - search result
     */
    public String searchInventory(int toolID) {
        String result = inventory.searchInventory(toolID);
        return result;
    }

    /**
     * Returns quantity string of tool.
     * 
     * @param toolID: int - tool ID to get quantity of
     * @return: String - quantity string of tool
     */
    public String checkItemQuantity(int toolID) {
        String result = inventory.checkItemQuantity(toolID);
        return result;
    }

    /**
     * Returns quantity string of tool. Note: Exact matches only
     * 
     * @param name: String - tool.description to get quantity of
     * @return: String - quantity string of tool
     */
    public String checkItemQuantity(String name) {
        String result = inventory.checkItemQuantity(name);
        return result;
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
}
