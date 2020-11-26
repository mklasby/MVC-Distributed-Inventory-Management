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

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public JSONArray encodeSearchQuery(ResultSet rs) {
        try {
            JSONArray jsonArray = new JSONArray();
            do {
                int toolID = rs.getInt("ToolID");
                String name = rs.getString("Name");
                String type = rs.getString("Type");
                int qty = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                int supplierID = rs.getInt("SupplierID");

                if (type.equalsIgnoreCase("electrical")) {
                    String powerType = rs.getString("PowerType");
                    Electrical electrical = new Electrical(toolID, name, type, qty, price, supplierID, powerType);
                    jsonArray.put((JSONObject) electrical.encode());
                } else {
                    Tool tool = new Tool(toolID, name, type, qty, price, supplierID);
                    jsonArray.put((JSONObject) tool.encode());
                }
            } while (rs.next());
            return jsonArray;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Generates order from orderLines
     * 
     * @param orderLines: ArrayList<OrderLine>: lines to be added to Order
     * @return: String - success/fail message
     */
    public String generateOrder(ArrayList<OrderLine> orderLines) {
        int orderID = this.getNextOrderID();
        String date = LocalDate.now().toString();
        // populate supplier fields
        for (OrderLine lines : orderLines) {
            int id = lines.getSupplierID();
            try {
                lines.setSupplier(this.suppliers.getSupplier(id).getCompanyName()); // set supplier field of lines
            } catch (Exception e) {
                continue;
            }
        }
        Order thisOrder = new Order(orderLines, date, orderID);
        orders.add(thisOrder);
        String message = this.writeOrder(thisOrder);
        return message;
    }

    /**
     * Helper function to generate next random 5 digit orderID
     * 
     * @return: int - 5 digit random number. Note: Not necessarily unique,
     *          uniqueness constraint will implemented when we connect to a DB.
     */
    private int getNextOrderID() {
        Random r = new Random(System.currentTimeMillis());
        return (r.nextInt(90000) + 10000);
    }
}