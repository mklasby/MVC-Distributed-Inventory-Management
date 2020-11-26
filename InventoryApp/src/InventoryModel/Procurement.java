package InventoryModel;

/**
 * Class to manage procurement side of app. Aggregation of VendorList and associated with Order. 
 * 
 * @author: Mike Lasby
 * @since: Oct 11, 2020
 * @version: 1.0
 */

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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

    public JSONObject generateOrder(ResultSet orderLines, ResultSet Order) {
        ArrayList<OrderLine> newOrderLines = createOrderLines(orderLines);
        Order encodedOrder = getEncodedOrder(newOrderLines, Order);
        return encodedOrder.encode();
    }

    /**
     * Helper method to create many order lines TODO create a method to make a
     * single orderLine
     * 
     * @param orderLines
     * @return
     */
    private ArrayList<OrderLine> createOrderLines(ResultSet orderLines) {
        try {
            ArrayList<OrderLine> newOrderLines = new ArrayList<OrderLine>();
            do {
                int toolId = orderLines.getInt("ToolID");
                int qty = orderLines.getInt("Quantity");
                int supplierId = orderLines.getInt("SupplierID");
                int orderId = orderLines.getInt("OrderID");
                newOrderLines.add(new OrderLine(toolId, supplierId, qty, orderId));
            } while (orderLines.next());
            return newOrderLines;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order getEncodedOrder(ArrayList<OrderLine> newOrderLines, ResultSet orderSet) {
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

    public JSONObject getNewOrder(ArrayList<OrderLine> orderLines, String date, int orderID) {
        Order order = new Order(orderLines, date, orderID);
        return order.encode();
    }

    public OrderLine createNewOrderLine(int toolId, int supplierId, int quantity, int orderId) {
        OrderLine newLine = new OrderLine(toolId, supplierId, quantity, orderId);
        return newLine;
    }

    /**
     * Increment quantity by 1
     * 
     * @param existingLine
     * @return (OrderLine)
     */
    public OrderLine incrementOrderLine(OrderLine existingLine) {
        existingLine.incrementOrderLine();
        return existingLine;
    }

    /**
     * Increment orderLine quantity by increment
     * 
     * @param existingLine (OrderLine) to increment
     * @param increment    (int) amount to increment by
     * @return (OrderLine)
     */
    public OrderLine incrementOrderLine(OrderLine existingLine, int increment) {
        existingLine.incrementOrderLine(increment);
        return existingLine;
    }

    // public JSONArray (ResultSet rs) {
    // try {
    // JSONArray jsonArray = new JSONArray();
    // do {
    // int toolID = rs.getInt("ToolID");
    // String name = rs.getString("Name");
    // String type = rs.getString("Type");
    // int qty = rs.getInt("Quantity");
    // double price = rs.getDouble("Price");
    // int supplierID = rs.getInt("SupplierID");

    // if (type.equalsIgnoreCase("electrical")) {
    // String powerType = rs.getString("PowerType");
    // Electrical electrical = new Electrical(toolID, name, type, qty, price,
    // supplierID, powerType);
    // jsonArray.put((JSONObject) electrical.encode());
    // } else {
    // Tool tool = new Tool(toolID, name, type, qty, price, supplierID);
    // jsonArray.put((JSONObject) tool.encode());
    // }
    // } while (rs.next());
    // return jsonArray;
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // /**
    // * Generates order from orderLines
    // *
    // * @param orderLines: ArrayList<OrderLine>: lines to be added to Order
    // * @return: String - success/fail message
    // */
    // public String generateOrder(ArrayList<OrderLine> orderLines) {
    // int orderID = this.getNextOrderID();
    // String date = LocalDate.now().toString();
    // // populate supplier fields
    // for (OrderLine lines : orderLines) {
    // int id = lines.getSupplierID();
    // try {
    // lines.setSupplier(this.suppliers.getSupplier(id).getCompanyName()); // set
    // supplier field of lines
    // } catch (Exception e) {
    // continue;
    // }
    // }
    // Order thisOrder = new Order(orderLines, date, orderID);
    // orders.add(thisOrder);
    // String message = this.writeOrder(thisOrder);
    // return message;
    // }

    // /**
    // * Helper function to generate next random 5 digit orderID
    // *
    // * @return: int - 5 digit random number. Note: Not necessarily unique,
    // * uniqueness constraint will implemented when we connect to a DB.
    // */
    // private int getNextOrderID() {
    // Random r = new Random(System.currentTimeMillis());
    // return (r.nextInt(90000) + 10000);
    // }
}