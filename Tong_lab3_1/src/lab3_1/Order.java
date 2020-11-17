package lab3_1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Order stores a collection of order line.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class Order {

	/**
	 * date for the order
	 */
	private LocalDate date;
	
	/**
	 * randomly generated 5-digit id for the order
	 */
	private int id;
	
	/**
	 * a list of order lines
	 */
	private List<OrderLine> orderLines;
	
	/**
	 * Construct an Order object.
	 */
	public Order() {
		date = LocalDate.now();
		id = 10000 + new Random().nextInt(90000);
		orderLines = new ArrayList<>();
	}
	
	/**
	 * Add given order line to Order.
	 * @param ol an order line
	 */
	public void addOrderLine(OrderLine ol) {
		orderLines.add(ol);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-30s", "ORDER ID:"));
		sb.append(id + "\n");
		sb.append(String.format("%-30s", "Date Ordered:"));
		sb.append(date + "\n\n");
		for (OrderLine ol : orderLines) {
			sb.append(ol + "\n");
		}
		return sb.toString().trim();
	}

}
