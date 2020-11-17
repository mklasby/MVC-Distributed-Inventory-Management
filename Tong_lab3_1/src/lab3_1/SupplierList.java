package lab3_1;

import java.util.Map;

/**
 * Supplier maintains the list of suppliers.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class SupplierList {

	/**
	 * List of suppliers
	 */
	private Map<Integer, Supplier> suppliers;
	
	/**
	 * Construct a SupplierList object
	 * @param suppliers Mapping of Supplier objects by their id
	 */
	public SupplierList(Map<Integer, Supplier> suppliers) {
		this.suppliers = suppliers;
	}
	
}
