package toolshop;

/**
 * Static utility  class to load files from ./lib/toolshop/ directory. 
 * Intended to be OS agnostic by using file.seperator and querying the working directory of project. 
 * 
 * @author: Mike Lasby
 * @since: Oct 11, 2020
 * @version: 1.0
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DBReader {
    private static String SUPPLIER_FILE = String.format("%slib%stoolshop%s", File.separator, File.separator,
            File.separator);
    private static String TOOL_FILE = String.format("%slib%stoolshop%s", File.separator, File.separator,
            File.separator);
    private static BufferedReader br;

    /**
     * Reads items from items.txt, creates new tools, creates inventory, returns
     * inventory instance.
     * 
     * @return Inventory object
     */
    public static Inventory readTools() {

        String userDir = System.getProperty("user.dir");
        ArrayList<Tool> inventory = new ArrayList<Tool>();
        File file = new File(userDir + TOOL_FILE, "items.txt");

        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.print("ERROR! File not found!\n");
        }

        String thisLine;
        try {
            while ((thisLine = br.readLine()) != null) {
                String[] tokens = thisLine.split(";");
                Tool thisTool = new Tool(Integer.parseInt(tokens[0]), tokens[1], Integer.parseInt(tokens[2]),
                        Double.parseDouble(tokens[3]), Integer.parseInt(tokens[4]));
                inventory.add(thisTool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Inventory(inventory);
    }

    /**
     * Read suppliers from suppliers.tx, creates new Suppliers, creates new
     * VendorList, and returns Procurement object.
     * 
     * @return Procurement object
     */
    public static Procurement readSuppliers() {
        String userDir = System.getProperty("user.dir");
        ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
        File file = new File(userDir + SUPPLIER_FILE, "suppliers.txt");
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("\nERROR! File not found!\n");
        }

        String thisLine;
        try {
            while ((thisLine = br.readLine()) != null) {
                String[] tokens = thisLine.split(";");
                Supplier thisSupplier = new Supplier(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]);
                suppliers.add(thisSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Procurement(new VendorList(suppliers));
    }

    /**
     * Function to receive order and decompose to an arrayList of orderLines that
     * can then be parsed by the Inventory to restock the included tools.
     * 
     * TODO: Implement me
     * 
     * @param fname: Stirng - name of order file to read. Note: Place order in
     *               ./lib/toolshop/orders
     * @return ArrayList<OrderLine> - all tools included in this order
     */
    public static ArrayList<OrderLine> readOrder(String fname) {
        // TODO: Implement reader
        return new ArrayList<OrderLine>();
    }

}
