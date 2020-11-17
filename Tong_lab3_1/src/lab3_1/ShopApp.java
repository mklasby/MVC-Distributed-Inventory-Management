package lab3_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;



/**
 * Shop application to manage the inventory of tools that it sells.
 * User can continuously choose options from an interactive console 
 * based menu, or quit the application. 
 * 
 * @author Tong Xu
 * @version 1.0
 * @since October 13, 2020
 */
public class ShopApp {
	/**
	 * read user input
	 */
	private BufferedReader br;
	
	/**
	 * the tool shop
	 */
	private Shop theShop;
	
	/**
	 * Construct a ShopApp object.
	 */
	public ShopApp() {
		br = new BufferedReader(new InputStreamReader (System.in));
		FileManager fm = new FileManager();
		List<Tool> tools = fm.readTools();
		Map<Integer, Supplier> suppliers = fm.getSuppliers();
		theShop = new Shop(new Inventory(tools), new SupplierList(suppliers));
	}
	
	/**
	 * Print menu options to console.
	 */
	private void printMenuOptions () {
		System.out.println("Opening shop application...\n\n");
		System.out.println("Menu: choose one of the following options");
		System.out.println("\t1. View all tool items");
		System.out.println("\t2. Search for tool by tool name");
		System.out.println("\t3. Search for tool by too id");
		System.out.println("\t4. Check item quantity");
		System.out.println("\t5. Reduce item quantity by 1");
		System.out.println("\t6. Quit");
		System.out.print("\n\nPlease enter your selection: ");
	}
	
	/**
	 * Run ShopApp application.
	 */
	public void run() {
		boolean toQuit = false;
		while (! toQuit) {
			printMenuOptions();		
			
			try {
				String option = br.readLine();
				while (! option.matches("[1-6]")) {
					System.out.print("\tInvalid selection, please enter again: ");
					option = br.readLine();
				}
				
				Tool foundTool;
				switch (Integer.parseInt(option)) {
					// view all tools
					case 1:
						theShop.viewItems();
						break;
					// search tool by name
					case 2:
						foundTool = searchToolbyName();
						if (foundTool != null) {
							System.out.println("Item found...");
							System.out.println(foundTool);
							System.out.println("\n");
						} else {
							System.out.println("Item not found...\n\n");
						}
						break;
					// search tool by id
					case 3:
						foundTool = searchToolbyID();
						if (foundTool != null) {
							System.out.println("Item found...");
							System.out.println(foundTool);
							System.out.println("\n");
						} else {
							System.out.println("Item not found...\n\n");
						}
						break;
					// check item quantity
					case 4:
						checkItemQty();
						break;
					// reduce item quantity
					case 5:
						reduceItemQty();
						break;
					// break
					case 6:
						toQuit = true;
						// before quitting, check inventory and generate order line for 
						// items low in stock
						System.out.println("\nQuitting application...");
						break;
				}
				
			} catch (IOException ioe) {
				 ioe.printStackTrace();
			}
		}
	}

	/**
	 * Helper function, find and return tool based on its name.
	 * @throws IOException
	 */
	private Tool searchToolbyName() throws IOException {
		System.out.print("Enter tool name (case insensitive): ");
		String name = br.readLine();
		// name must only contain the alphabet and space
		while (! name.matches("[A-Za-z ]+")) {
			System.out.print("\tInvalid selection, please enter again: ");
			name = br.readLine();
		}
		
		return theShop.searchToolByName(name);
	}
	
	/**
	 * Helper function, find and return tool based on its id.
	 * @throws IOException
	 */
	private Tool searchToolbyID() throws IOException {
		System.out.print("Enter the 4-digit tool id: ");
		String id = br.readLine();
		// name must consist of 4 digits
		while (! id.matches("[0-9]{4}")) {
			System.out.print("\tInvalid selection, please enter again: ");
			id = br.readLine();
		}
		
		return theShop.searchToolByID(Integer.parseInt(id));
	}
	
	/**
	 * Helper function to find tool by allowing user to enter either tool name or id.
	 * @return the Tool object found either by name or id
	 * @throws IOException
	 */
	private Tool searchItem() throws IOException {
		System.out.print("Enter 1 for name or 2 for id: ");
		String num = br.readLine();

		while (! num.matches("[12]")) {
			System.out.print("\tInvalid selection, please enter again: ");
			num = br.readLine();
		}
		
		int option = Integer.parseInt(num);
		Tool tool;
		if (option == 1) {
			tool = searchToolbyName();
		} else {
			tool = searchToolbyID();
		}
		return tool;
	}
	
	/**
	 * Helper function to check item quantity. 
	 * @throws IOException
	 */
	private void checkItemQty() throws IOException {
		Tool tool = searchItem();
		if (tool != null) {
			System.out.printf("Item found: quantity = %d\n\n\n", tool.getQty());
		} else {
			System.out.println("Item not found...\n\n");
		}
	}
	
	/**
	 * Helper function to reduce item quantity by one.
	 * @throws IOException
	 */
	private void reduceItemQty() throws IOException {
		Tool tool = searchItem();
		if (tool != null) {
			System.out.printf("Item found...\nInitial quantity = %d\n\n", tool.getQty());
			tool.reduceItemQty();
			theShop.manageInventory(tool);
			System.out.printf("\n\nNew quantity = %d\n\n\n", tool.getQty());
		} else {
			System.out.println("Item not found...\n\n");
		}
	}
	
	public static void main(String[] args) {
		ShopApp app = new ShopApp();
		app.run();
	}
	
}