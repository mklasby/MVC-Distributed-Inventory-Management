package InventoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to control function of back end. Conducts back end modules as directed by app. Returns primitive data types to app to reduce coupling of lower level classes. 
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InvMgmt {
    public final static int TARGET_STOCK_QUANTITY = 50;
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
     * @param tool  (JSONObject) tool representation
     * @param order (ResultSet) today's order
     * @return (JSONObject)
     */
    public JSONObject restock(JSONObject tool, int orderId) {
        try {
            int toolId = tool.getInt("ToolID");
            int supplierId = tool.getInt("SupplierID");
            int toolQuantity = tool.getInt("Quantity");
            int quantity = TARGET_STOCK_QUANTITY - toolQuantity;
            OrderLine orderLine = new OrderLine(toolId, supplierId, quantity, orderId);
            return orderLine.encode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Increment existing orderLine
     * 
     * @param tool (JSONObject) Tool to be incremented. required to calculate new
     *             quantity
     * @param rs   (ResultSet) exiting orderLine from db
     * @return (JSONObject) orderLine to put in db
     */
    public JSONObject incrementOrderLine(JSONObject tool, ResultSet rs) {
        try {
            int existingQuantity = tool.getInt("Quantity");
            int newQuantity = TARGET_STOCK_QUANTITY - existingQuantity;
            return procurement.incrementOrderLine(newQuantity, rs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray encodeToolSearchQuery(ResultSet rs) {
        return inventory.encodeSearchQuery(rs);
    }

    public Object encodeCustomerSearchQuery(ResultSet rs) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Get new orderline in form of JSON Object
     * 
     * @param tool         (JSONObject) tool to create orderLine for
     * @param forThisOrder (ResultSet) order from db where IsOrdered==false
     * @return (JSONObject)
     */
    public JSONObject getNewOrderLine(JSONObject tool, ResultSet forThisOrder) {
        try {
            int toolId = tool.getInt("ToolID");
            int supplierId = tool.getInt("SupplierID");
            int quantity = tool.getInt("Quantity");
            int orderId = forThisOrder.getInt("OrderID");
            int newQuantity = TARGET_STOCK_QUANTITY - quantity;
            return new OrderLine(toolId, supplierId, newQuantity, orderId).encode();
        } catch (JSONException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}