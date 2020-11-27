package InventoryModel;

import java.util.ArrayList;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to manage procurement side of app.
 * 
 * @author Mike Lasby && Tong Xu
 * @since November 26, 2020
 * @version 2.0
 */
public class Procurement {
    private ArrayList<Order> orders;
    private ArrayList<Supplier> suppliers;

    Procurement() {
        orders = new ArrayList<Order>();
        suppliers = new ArrayList<Supplier>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Order getOrder(int orderId) {
        for (Order order : orders) {
            if (order.getOrderID() == orderId) {
                return order;
            }
        }
        return null;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    /**
     * Generate order for all pending orderLines and return as JSON
     * 
     * @param orderLines (ResultSet) pending orderlines
     * @param Order      (ResultSet) order from db where isOrdered==false;
     * @return (JSONObject) this order in JSON form to PUT in db
     */
    public JSONObject generateOrder(ResultSet orderLines, ResultSet Order) {
        ArrayList<OrderLine> newOrderLines = createOrderLines(orderLines);
        Order encodedOrder = createOrder(newOrderLines, Order);
        return encodedOrder.encode();
    }

    /**
     * Helper method to create many order lines.
     * 
     * @param orderLines (ResultSet) resultSet containing many orderLines
     * @return
     */
    private ArrayList<OrderLine> createOrderLines(ResultSet orderLines) {
        try {
            ArrayList<OrderLine> newOrderLines = new ArrayList<OrderLine>();
            do {
                newOrderLines.add(new OrderLine(orderLines));
            } while (orderLines.next());
            return newOrderLines;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to create order once orderLines are decoded
     * 
     * @param newOrderLines (ArrayList<OrderLine) order lines to include with this
     *                      order
     * @param orderSet      (ResultSet) this order from db (MUST HAVE ID AND DATE!)
     * @return (Order) order
     */
    private Order createOrder(ArrayList<OrderLine> newOrderLines, ResultSet orderSet) {
        try {
            int orderId = orderSet.getInt("OrderID");
            String date = orderSet.getString("Date");
            Order order = new Order(newOrderLines, date, orderId);
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a NEW orderLine with passed attributes
     * 
     * @param toolId     (int) toolId to order
     * @param supplierId (int) supplier of tool
     * @param quantity   (int) quantity to order
     * @param orderId    (int) order that this line belongs to
     * @return (JSONObject) representation of new orderLine for DB
     */
    public JSONObject createNewOrderLine(int toolId, int supplierId, int quantity, int orderId) {
        OrderLine newLine = new OrderLine(toolId, supplierId, quantity, orderId);
        return newLine.encode();
    }

    /**
     * Increment orderLine quantity by increment
     * 
     * @param newQuantity (OrderLine) to increment
     * @param rs          (int) amount to increment by
     * @return (OrderLine)
     */
    public JSONObject incrementOrderLine(int newQuantity, ResultSet rs) {
        OrderLine newLine = new OrderLine(rs);
        newLine.setQuantity(newQuantity);
        return newLine.encode();
    }
}