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

    OrderLine(int toolId, int supplierId, int quantity) {
        super();
        this.toolId = toolId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        putFields();
    }

    OrderLine(ResultSet result) {
        // TODO
        super();
        // this.toolId = toolId;
        // this.supplierId = supplierId;
        // this.quantity = quantity;
        // putFields();
    }

    private void putFields() {
        try {
            put("ToolID", toolId);
            put("SupplierID", supplierId);
            put("Quantity", quantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add one to the this.amount
     */
    public void incrementOrderLine() {
        this.quantity++;
    }

    public String toDescriptionString() {
        String message = String.format("Tool Id: %d, Supplier Id: %d, Quantity: %d", toolId, supplierId, quantity);
        return message;
    }

    public JSONObject encode() {
        return this;
    }
}
