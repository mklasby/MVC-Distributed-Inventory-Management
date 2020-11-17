package toolshop;

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
    private ArrayList<Order> orders = new ArrayList<Order>();
    private VendorList vendors;

    Procurement(VendorList vendors) {
        this.vendors = vendors;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public VendorList getVendors() {
        return vendors;
    }

    public void setVendors(VendorList v) {
        this.vendors = v;
    }

    /**
     * Add order to this.orders
     * 
     * @param order: Order - order to be added
     * @return: String - success message
     */
    public String addOrder(Order order) {
        orders.add(order);
        return String.format("Order %d added to Procurement Order Database.\n", order.getOrderID());
    }

    /**
     * Deletes order from this.orders
     * 
     * @param order: Order - order to be deleted
     * @return: String - success/fail message
     */
    public String deleteOrder(Order order) {
        Order deletedOrder = null;
        for (Order o : orders) {
            if (o.getOrderID() == order.getOrderID()) {
                deletedOrder = o;
            }
        }
        if (deletedOrder == null) {
            return String.format("ERROR! Order %d not found in Procurement Order Database.\n", order.getOrderID());
        } else {
            orders.remove(order);
            return String.format("Success. Order %d removed from Procurement Order Database.\n", order.getOrderID());
        }
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
                lines.setSupplier(this.vendors.getVendor(id).getCompanyName()); // set supplier field of lines
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

    /**
     * Helper method to get order based on search parameters.
     * 
     * @param value: <V> - Search query, expecting string or int.
     * @throws Exception - If type other than string/int provided
     * @return: Order - order if found, null if not
     */
    private <V> Order getOrder(V value) throws Exception {
        Order gotOrder = null;

        if (value instanceof String) {
            for (Order o : this.orders) {
                if (o.getDate().equals(value)) {
                    gotOrder = o;
                    break;
                }
            }
            if (gotOrder == null) {
                return null;
            }
            return gotOrder;

        } else if (value instanceof Integer) {
            for (Order o : this.orders) {
                if (o.getOrderID() == (int) value) {
                    gotOrder = o;
                    break;
                }
            }

            if (gotOrder == null) {
                return null;
            }
            return gotOrder;

        } else {
            throw new Exception("\nERROR! Expecting String or Int in Procurement.getOrder!\n");
        }
    }

    /**
     * Helper method to invoke orderWriter.writeOrder and get status message
     * 
     * @param order: Order - order to write
     * @return: String - success/fail message
     */
    private String writeOrder(Order order) {
        String message = OrderWriter.writeOrder(order);
        return message;
    }
}