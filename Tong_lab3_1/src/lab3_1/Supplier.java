package lab3_1;
/**
 * Represent a tool supplier and keeps track of its id, company name,
 * address, and sale contact.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class Supplier {
	
	/**
	 * supplier id
	 */
	private int id;
		
	/** 
	 * company name
	 */
	private String name;
	
	/**
	 * company address
	 */
	private String addr;
	
	/**
	 * sales contact
	 */
	private String contact;
	
	/**
	 * Construct a Supplier object with specified information
	 * @param id supplier id
	 * @param name company name
	 * @param addr address
	 * @param contact sales contact
	 */
	public Supplier (int id, String name, String addr, String contact) {
		this.id = id;
		this.name = name;
		this.addr = addr;
		this.contact = contact;
	}

	/**
	 * Return the supplier name.
	 * @return supplier name
	 */
	public String getName() {
		return name;
	}
	
}
