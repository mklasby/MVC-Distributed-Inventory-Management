package InventoryModel;

import java.sql.ResultSet;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Data container class to populate Orders and provide a low-coupled transfer
 * mechanism between inventory and procurement branches.
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0
 */

public class OrderLine extends JSONObject {

    private int toolId;
    private int supplierId;
    private int quantity;
    private int orderId;

    public OrderLine(int toolId, int supplierId, int quantity, int orderId) {
        super();
        this.toolId = toolId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    private void putFields() {
        try {
            put("ToolID", toolId);
            put("SupplierID", supplierId);
            put("Quantity", quantity);
            put("OrderID", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add one to the this.quantity
     */
    public void incrementOrderLine() {
        this.quantity++;
    }

    /**
     * Adds quant to the this.quantity
     */
    public void incrementOrderLine(int quant) {
        this.quantity = quantity + quant;
    }

    public String toDescriptionString() {
        String message = String.format("Tool Id: %d, Supplier Id: %d, Quantity: %d", toolId, supplierId, quantity);
        return message;
    }

    public JSONObject encode() {
        putFields();
        return this;
    }
}
