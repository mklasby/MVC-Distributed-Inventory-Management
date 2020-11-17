package lab3_1;

import java.util.List;

/**
 * Inventory maintains the list of tools in the shop.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class Inventory {

	/**
	 * List of tools
	 */
	private List <Tool> tools;
	/**
	 * Order for the day
	 */
	private Order order;
	
	/**
	 * if inventory have been reviewed 
	 */
	private boolean ordered;
	
	/**
	 * Construct an Inventory object with the given list of Tool items
	 * @param tools a List of Tool items
	 */
	public Inventory(List<Tool> tools) {
		this.tools = tools;
		order = new Order();
		ordered = false;
	}

	/**
	 * Print all Tool items in inventory including their id, name, and quantity.
	 */
	public void printItems() {
		for (Tool tool : tools) {
			System.out.println(tool);
		}
	}

	/**
	 * Search for tool with the given name.
	 * @param name tool name
	 * @return the Tool object with the given name, or null if no Tool object is found
	 */
	public Tool searchToolbyName(String name) {
		for (Tool tool : tools) {
			if (tool.getName().equalsIgnoreCase(name)) {
				return tool;
			}
		}
		return null;
	}

	/**
	 * Search for tool with the given id.
	 * @param id tool id
	 * @return the Tool object with the given name, or null if no Tool object is found
	 */
	public Tool searchToolbyID (int id) {
		for (Tool tool : tools) {
			if (tool.getID() == id) {
				return tool;
			}
		}
		return null;
	}

	/**
	 * Check item quantity and generate an order line if item is low in quantity.
	 * @param soldTool sold item (quantity decreased by one)
	 */
	public void manageInventory(Tool soldTool) {
		// first sale (decrease quantity) would review and generate an order line for all 
		// tool items low in inventory
		if (ordered == false) {
			for (Tool tool : tools) {
				if (tool.getQty() < 40) {
					order.addOrderLine(new OrderLine(tool));
					tool.addOrder();
				}
			}
			ordered = true;
		// for additional sales, if an order has not been placed for the sold item, it will
		// be reviewed and a order will be placed
		} else {
			if (! soldTool.isOrdered() && soldTool.getQty() < 40) {
				order.addOrderLine(new OrderLine(soldTool));
				soldTool.addOrder();
			}
		}

		System.out.print(order);
	}
	
}
