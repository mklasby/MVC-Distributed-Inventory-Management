package toolshop;

/**
 * Data container class for Orders. 
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0 
 */

import java.util.ArrayList;

public class Order {
    private ArrayList<OrderLine> items;
    private String date;
    private int orderID;

    public Order(ArrayList<OrderLine> orderLines, String date, int orderID) {
        this.items = orderLines;
        this.setDate(date);
        this.setOrderID(orderID);
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
