package InventoryModel;

import java.sql.ResultSet;

/**
 * Class to control function of back end. Conducts back end modules as directed by app. Returns primitive data types to app to reduce coupling of lower level classes. 
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    // TODO: Move Inv controller logic to this class?
    // TODO: MOVE whole class to inv controller?
    // public JSONObject searchInventory(ResultSet query){

    // }

    /**
     * Get JSON object representation of Order
     * 
     * @param orderLines (ResultSet) orderLines from db to encode
     * @param order      (ResultSet) today's order with date and Id
     * @return (JSONObject) representation of Order
     */
    public JSONObject generateOrder(ResultSet orderLines, ResultSet order) {
        return procurement.generateOrder(orderLines, order);
    }

    /**
     * Returns JSON object of a new OrderLine
     * 
     * @param tool     (JSONObject) tool representation
     * @param quantity (int) quantity to order
     * @param orderId  (orderId) today's orderId
     * @return (JSONObjet)
     */
    public JSONObject restock(JSONObject tool) {
        try {
            int toolId = tool.getInt("ToolID");
            int supplierId = tool.getInt("SupplierID");
            OrderLine orderLine = new OrderLine(toolId, supplierId, quantity, orderId);
            return orderLine.encode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Increment orderLine quantity by increment
     * 
     * @param existingLine (OrderLine) to increment
     * @param increment    (int) amount to increment by
     * @return (OrderLine)
     */
    public OrderLine incrementOrderLine(JSONObject tool, ResultSet orderLine) {
        existingLine.incrementOrderLine(increment);
        return existingLine;
    }

    // /**
    // * Generates order from inventory.pendingOrders and clears pendingOrders once
    // * order has been generated.
    // *
    // * @return: String - success/fail message
    // */
    // public String generateOrder() {
    // String result =
    // this.procurement.generateOrder(this.inventory.getPendingOrders());
    // // clear pending orders in Inventory now that we have collected and issued
    // into
    // // an order
    // this.inventory.setPendingOrders(new ArrayList<OrderLine>());
    // return result;
    // }
    public JSONArray encodeToolSearchQuery(ResultSet rs) {
        return inventory.encodeSearchQuery(rs);
    }

    public Object encodeCustomerSearchQuery(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }
}
