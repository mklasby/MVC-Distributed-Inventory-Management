package lab3_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for initializing the database for tools and suppliers.
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 *
 */
public class FileManager {

	/**
	 * Mapping of Supplier objects by their id
	 */
	private Map <Integer, Supplier> suppliers;
	
	/**
	 * Construct a FileManager object
	 */
	public FileManager() {
		// instantiate the attribute suppliers which is used in readTools()
		readSuppliers();
	}
	
	/**
	 * Read from text file item.txt (stored in same directory) and return 
	 * a list of Tool objects available in the shop.
	 * @return a list of Tool objects
	 */
	public List<Tool> readTools (){
		
		String fileDir = "items.txt";
		List<Tool> db = new ArrayList<>();
		
		try (BufferedReader br = 
				new BufferedReader(new FileReader(fileDir))) {
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] tuple = line.split(";");
				assert tuple.length == 5;
				
				int id = Integer.parseInt(tuple[0]);
				String name = tuple[1];
				int qty = Integer.parseInt(tuple[2]);
				double price = Double.parseDouble(tuple[3]);
				int supplierID = Integer.parseInt(tuple[4]);
				
				Supplier supplier = suppliers.get(supplierID);

				db.add( new Tool (id, name, qty, price, supplier) );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db;
	}
	
	/**
	 * Read from text file suppliers.txt (stored in same directory) and  
	 * return a list of Supplier objects available in the shop.
	 * @return a list of Supplier objects
	 */
	private Map<Integer, Supplier> readSuppliers() {
		String fileDir = "suppliers.txt";
		Map<Integer, Supplier> db = new HashMap<>();
		
		try (BufferedReader br = 
				new BufferedReader(new FileReader(fileDir))) {
			
			String line;
			while ((line = br.readLine()) != null) {
				String[] tuple = line.split(";");
				assert tuple.length == 4;
				
				int id = Integer.parseInt(tuple[0]);
				String name = tuple[1];
				String addr = tuple[2];
				String contact = tuple[3];
				
				db.put( id, new Supplier (id, name, addr, contact) );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		suppliers = db;
		return db;  
	}
	
	/**
	 * Return the list of Supplier objects.
	 * @return a list of supplier objects
	 */
	public Map<Integer, Supplier> getSuppliers() {
		return new HashMap<>(suppliers);
	}
}
