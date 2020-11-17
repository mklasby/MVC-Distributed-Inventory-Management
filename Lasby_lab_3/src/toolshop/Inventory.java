package toolshop;

/**
 * Inventory class to manage tool objects and collect pending orders. 
 * Aggregation of tool objects. 
 * 
 * @author: Mike Lasby
 * @since: Oct 11, 2020
 * @version: 1.0
 */

import java.util.ArrayList;
import java.util.LinkedList;

public class Inventory {
    ArrayList<Tool> tools;
    ArrayList<OrderLine> pendingOrders = new ArrayList<OrderLine>();

    Inventory(ArrayList<Tool> tools) {
        this.tools = tools;
    }

    public ArrayList<Tool> getTools() {
        return tools;
    }

    public void setTools(ArrayList<Tool> t) {
        this.tools = t;
    }

    public ArrayList<OrderLine> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(ArrayList<OrderLine> p) {
        this.pendingOrders = p;
    }

    @Override
    public String toString() {
        String message = "";
        for (Tool t : tools) {
            System.out.print(t.toString());
            message.concat(t.toString());
        }
        return message;
    }

    /**
     * Searches inventory by tool.description. Partial / nearest matches returned
     * 
     * @param name: String - string to search tool.description for.
     * @return String: List of best matches ranked in descending order OR no match
     *         found message.
     */
    public String searchInventory(String name) {
        LinkedList<String> results = new LinkedList<String>();
        char[] query = name.toLowerCase().toCharArray();
        int hits;

        for (int i = query.length; i > 0; i--) {
            for (Tool t : tools) {
                char[] toolName = t.getDescription().toLowerCase().toCharArray();
                hits = 0;
                for (int j = 0; j < i; j++) {
                    if (j >= toolName.length) {
                        break;
                    } else if (query[j] == toolName[j]) {
                        hits++;
                    }
                }

                boolean inResults = false;
                if (hits == i) {
                    for (String s : results) {
                        if (s.contains(t.getDescription())) {
                            inResults = true;
                            break;
                        }
                    }
                    if (!inResults) {
                        results.add(t.toString());
                    }
                }
            }
        }
        String message = "";
        for (String result : results) {
            message = message.concat(result);
        }
        if (message == "") {
            return "No Search Results\n";
        }
        return message;
    }

    /**
     * Method to search inventory by tool.toolID. Only exact matches are returned.
     * 
     * @param toolID: int - toolID to search for.
     * @return: String - Found tool match or no match message.
     */
    public String searchInventory(int toolID) {
        Tool soldTool = getTool(toolID);
        if (soldTool == null) {
            return toolNotFound(toolID);
        } else {
            return soldTool.toString();
        }
    }

    public String checkItemQuantity(int toolID) {
        Tool tool = this.getTool(toolID);
        if (tool == null) {
            return this.toolNotFound(toolID);
        } else {
            return tool.toQuantityString();
        }
    }

    public String checkItemQuantity(String name) {
        Tool tool = null;
        for (Tool t : tools) {
            if (t.getDescription().toLowerCase().equals(name.toLowerCase())) {
                tool = t;
            }
        }
        if (tool == null) {
            return this.toolNotFound(name);
        } else {
            return tool.toQuantityString();
        }
    }

    /**
     * Sells 1 quantity of tool. Checks if we have stock and if the tool must be
     * restocked today after this sale.
     * 
     * @param toolID: int - tool to sell
     * @return: String - success/failure message
     */
    public String makeSale(int toolID) {
        Tool soldTool = getTool(toolID);
        String success = null;
        if (soldTool == null) {
            return toolNotFound(toolID);
        } else {
            String toolName = soldTool.getDescription();
            if (soldTool.getQuantity() <= 0) {
                return String.format("ERROR! %s (ToolID: %d) is out of stock!", toolName, toolID);
            } else {
                soldTool.setQuantity(soldTool.getQuantity() - 1);
                success = String.format(
                        "Success, %s (ToolID: %d) has been sold to one lucky customer! %d of %s remaining in stock.\n",
                        toolName, toolID, soldTool.getQuantity(), toolName);
                if (soldTool.getQuantity() < soldTool.getLowSupply()) { // low supply?
                    restock(soldTool);
                    success.concat(String.format("%s (ToolID: %d) is low in stock. I have added %s to today's Order.\n",
                            toolName, toolID, toolName));
                }
            }
        }
        return success;
    }

    /**
     * Method to return 1 quantity of a tool. TODO: Implement me
     * 
     * @param toolID: int - toolID to be returned
     * @return: String - success/failure message
     */
    public String returnTool(int toolID) {
        return "Please upgrade to our Premium Plan to unlock this feature!\n";
    }

    /**
     * Method to receive and order and add stock to all tools included in order.
     * TODO: Implement me
     * 
     * @param order: ArrayList<OrderLine> - orderLines received
     * @return: String - success/failure message
     */
    public String receiveOrder(ArrayList<OrderLine> order) {
        return "Please upgrade to our Premium Plan to unlock this feature!\n";
    }

    /**
     * Checks inventory for tools that are in low stock and adds them to pending
     * orders.
     */
    public void populatePendingOrder() {
        for (Tool t : this.tools) {
            if (t.getQuantity() < t.getLowSupply()) {
                restock(t);
            }
        }
    }

    /**
     * Restocks tool by adding it to pending orders list. First checks if order line
     * matching this tool already exists, if so, just increments that orderLine.
     * 
     * @param tool: Tool - tool instance to restock
     */
    private void restock(Tool tool) {
        int toolID = tool.getToolID();

        // Check if already ordered, if so, simply increment orderLine quantity
        for (OrderLine line : this.pendingOrders) {
            if (line.getToolID() == toolID) {
                line.incAmount();
                System.out.printf("Incrementing today's order for %s\n", line.getDescription());
                return;
            }
        }

        String desc = tool.getDescription();
        int amt = tool.getTargetSupply() - tool.getQuantity();
        int supplierID = tool.getSupplierID();
        String supplier = "Supplier ID not found!\n"; // To be populated by Procurement when Order is generated
        OrderLine line = new OrderLine(toolID, desc, amt, supplierID, supplier);
        this.pendingOrders.add(line);
        return;

    }

    /**
     * Helper function to return tool instance based on id
     * 
     * @param toolID: int - tool.toolID to get
     * @return: tool - tool object matching id or null if not match
     */
    private Tool getTool(int toolID) {
        Tool foundTool = null;
        for (Tool t : tools) {
            if (t.getToolID() == toolID) {
                foundTool = t;
                break;
            }
        }
        return foundTool;
    }

    /**
     * Helper function to return tool instance based on tool.description
     * 
     * @param name: String - tool.description to get
     * @return: tool - tool object matching id or null if not match
     */
    private Tool getTool(String name) {
        Tool foundTool = null;
        for (Tool t : tools) {
            if (t.getDescription().equals(name)) {
                foundTool = t;
                break;
            }
        }
        return foundTool;
    }

    /**
     * Message builder if tool is not found in one of the search methods above.
     * 
     * @param query: <V> - Generic type for query, expecting int or String
     * @return: String - Error message of tool not found
     */
    private <V> String toolNotFound(V query) {
        if (query instanceof Integer) {
            return String.format("ERROR! ToolID %d not found in inventory!\n", query);
        } else if (query instanceof String) {
            return String.format("ERROR! Tool Name %s not found in inventory!\n", query);
        }
        return "ERROR IN GENERIC toolNotFound!\n";
    }

}
