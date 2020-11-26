package InventoryModel;

/**
 * Data container class for Orders. 
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0 
 */

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Order extends JSONObject {
    private ArrayList<OrderLine> items;
    private String date;
    private int orderID;

    // TODO: is ordered?
    // public Order(ResultSet orderLines, ResultSet order) {
    // super();
    // this.items = orderLines;
    // this.setDate(date);
    // this.setOrderID(orderID);
    // }

    public Order(ArrayList<OrderLine> orderLines, String date, int orderID) {
        super();
        this.items = orderLines;
        this.setDate(date);
        this.setOrderID(orderID);
    }

    public void putFields() {
        try {
            this.put("OrderLines", items);
            this.put("Date", date);
            this.put("OrderID", orderID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public ArrayList<OrderLine> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<OrderLine> items) {
        this.items = items;
    }

}
