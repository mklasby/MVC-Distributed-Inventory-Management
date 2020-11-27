package InventoryModel;

/**
 * Data container class for Orders. 
 * 
 * @author Mike Lasby & Tong Xu
 * @since  Nov 29, 2020
 * @version  2.0 
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Order extends JSONObject {
    private ArrayList<OrderLine> orderLines;
    private String date;
    private int orderID;
    private boolean isOrdered = false;

    public Order(ArrayList<OrderLine> orderLines, String date, int orderID) {
        super();
        this.orderLines = orderLines;
        this.setDate(date);
        this.setOrderID(orderID);
    }

    public void putFields() {
        JSONArray JSONOrderLines = new JSONArray();
        for (OrderLine line : orderLines) {
            JSONOrderLines.put(line.encode());
        }
        try {
            this.put("isOrdered", isOrdered);
            this.put("OrderLines", JSONOrderLines);
            this.put("Date", date);
            this.put("OrderID", orderID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepare order for ordering and recording details
     * 
     * @return (JSONObject) representation of order
     */
    public JSONObject encode() {
        this.isOrdered = true;
        this.putFields();
        return this;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public String getDate() {
        return this.date;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return this.orderLines;
    }

    public void setOrderLine(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

}
