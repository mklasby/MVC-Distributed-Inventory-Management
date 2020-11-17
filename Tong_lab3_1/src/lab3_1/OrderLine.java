package lab3_1;

/**
 * Order Line consists of information for the order of a tool item that is low in stock.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class OrderLine {

	private String toolName;
	private int qty;
	private String supplier;
	
	/**
	 * Construct an order line for the given tool item.
	 * @param tool Tool object
	 */
	public OrderLine(Tool tool) {
		toolName = tool.getName();
		qty = 50 - tool.getQty();
		supplier = tool.getSupplier().getName();
	}

	/**
	 * Return information about the order line including item description, amount ordered,
	 * and supplier name.
	 */
	@Override
	public String toString() {
		String str = String.format("%-30s", "Item description:");
		str += toolName + "\n";
		str += String.format("%-30s", "Amount ordered:");
		str += qty + "\n";
		str += String.format("%-30s", "Supplier:");
		str += supplier + "\n";
		return str;
	}
}
