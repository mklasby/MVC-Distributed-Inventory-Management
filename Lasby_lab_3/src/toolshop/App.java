package toolshop;

/**
 * CLI front end interface responsible for displaying Views. Aggregation of InvMgmt. 
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    private BufferedReader br;
    private InvMgmt invMgmt;

    App(InvMgmt im) {
        br = new BufferedReader(new InputStreamReader(System.in));
        setInvMgmt(im);
    }

    public InvMgmt getInvMgmt() {
        return invMgmt;
    }

    public void setInvMgmt(InvMgmt invMgmt) {
        this.invMgmt = invMgmt;
    }

    /**
     * Display info on all items in inventory
     */
    public void displayInventory() {
        this.clear();
        this.invMgmt.printInventory();
        return;
    }

    /**
     * Search inventory by Tool.description. Displays a collection of objects with
     * matches listed in descending order. Ie., best match listed at top of page.
     * 
     * @param name: String - string to search tool.description for.
     */
    public void searchInventory(String name) {
        this.clear();
        String results = this.invMgmt.searchInventory(name);
        System.out.printf("Search Result: %s\n%s", name, results);
        return;
    }

    /**
     * Search inventory by Tool.toolID. Only displays exact matches.
     * 
     * @param id - int: ToolID to search for
     */
    public void searchInventory(int id) {
        this.clear();
        String results = this.invMgmt.searchInventory(id);
        System.out.printf("Search Result: %d\n%s", id, results);
        return;
    }

    /**
     * Sells 1 quantity of tool corresponding to toolID. Displays message string
     * about sale success and if new stock was ordered. If sale is successful,
     * tool.quantity will be deincremented by 1.
     * 
     * @param toolID: int - tool to be sold.
     */
    public void makeSale(int toolID) {
        this.clear();
        String result = this.invMgmt.makeSale(toolID);
        System.out.printf("%s\n", result);
        return;

    }

    /**
     * Returns 1 quantity of tool corresponding to toolID. TODO: Implement this
     * function, currently just a placeholder.
     * 
     * @param toolID: int - toolID to return
     */
    public void returnTool(int toolID) {
        this.clear();
        String result = this.invMgmt.returnTool(toolID);
        System.out.printf("%s\n", result);
        return;
    }

    /**
     * Check item quantity of tool corresponding to toolID. Displays message of
     * current stock of tool.
     * 
     * @param toolID: int - toolID to check
     */
    public void checkItemQuantity(int toolID) {
        this.clear();
        String result = this.invMgmt.checkItemQuantity(toolID);
        System.out.printf("%s\n", result);
        return;

    }

    /**
     * Check item quantity of tool corresponding to name = tool.description.
     * Displays message of current stock of tool.
     * 
     * @param name: String - tool.description to check quantity of
     */
    public void checkItemQuantity(String name) {
        this.clear();
        String result = this.invMgmt.checkItemQuantity(name);
        System.out.printf("%s\n", result);
        return;
    }

    /**
     * Generates Order instance of all pendingOrders currently held in inventory.
     * Displays a success/failure message upon completion.
     */
    public void generateOrder() {
        this.clear();
        String result = this.invMgmt.generateOrder();
        System.out.printf("%s\n", result);
        return;
    }

    /**
     * Parses order and increments Tools based on quantity received. TODO: Implement
     * this.
     * 
     * @param fname
     */
    public void receiveOrder(String fname) {
        this.clear();
        String result = invMgmt.receiveOrder(fname);
        System.out.printf("%s\n", result);
        return;
    }

    /**
     * Displays menu options.
     */
    private void displayMenu() {
        System.out.printf("~Lasby Inventory MGMT System~\n%s\n\n", ("-".repeat(80)));
        System.out.print("1.  Display Inventory\n2.  Search for Tool by Name\n"
                + "3.  Search for Tool by ID\n4.  Check tool Quantity by Name\n"
                + "5.  Check Tool Quantity by ID\n6.  Make Sale\n" + "7.  Return Tool\n8.  Generate Order\n"
                + "9.  Generate Order for All Low Stock Items\n10. Receive Order\n" + "\"q\" Quit App\n>>> ");
    }

    /**
     * Displays menu return message and listens for user input to return control to
     * app.run
     */
    private void returnToMenu() {
        System.out.printf("%s\nPress any key to return to the main menu\n", "-".repeat(80));
        String input = this.getString();
        if (input != null) {
            return;
        }
    }

    /**
     * Listens for user input at menu and orchestrates basic input sanitization
     */
    private void getMenuChoice() {
        String s = null;
        int input = 0;
        String inputError = "ERROR! PLEASE SELECT FROM ONE OF THE MENU CHOICES ABOVE\n>>> ";
        while (true) {
            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (s.equals("q") || s.equals("Q")) {
                System.out.print("Goodbye!\n");
                System.exit(0);
            } else {
                try {
                    input = Integer.parseInt(s);
                } catch (Exception e) {
                    System.out.print(inputError);
                    s = null;
                    continue;
                }
            }

            switch (input) {
                case 1:
                    this.displayInventory();
                    break;
                case 2:
                    this.searchInventory(this.getName());
                    break;
                case 3:
                    this.searchInventory(getID());
                    break;
                case 4:
                    this.checkItemQuantity(this.getName());
                    break;
                case 5:
                    this.checkItemQuantity(this.getID());
                    break;
                case 6:
                    this.makeSale(this.getID());
                    break;
                case 7:
                    this.returnTool(this.getID());
                    break;
                case 8:
                    this.generateOrder();
                    break;
                case 9:
                    this.generateOrderForAll();
                    break;
                case 10:
                    System.out.print(
                            "Please enter the order file name and ensure that the order is placed in the /lib/orders directory.\n");
                    this.receiveOrder(this.getString());
                    break;
                default:
                    System.out.print(inputError);
                    s = null;
            }
            this.returnToMenu();
            this.clear();
            this.displayMenu();
        }
    }

    /**
     * Generates an order for all tools where tool.quantity < tool.lowSupply.
     * Displays success/failure message upon completion.
     */
    private void generateOrderForAll() {
        this.clear();
        String message = this.invMgmt.generateOrderForAll();
        System.out.print(message);
        return;
    }

    /**
     * Helper function to display get ID message.
     * 
     * @return: int - the int provided by user
     */
    private int getID() {
        System.out.print("\nPlease enter the tool ID\n>>> ");
        return this.getInt();
    }

    /**
     * Helper function to request an integer from user until good input is provided
     * 
     * @return: int - the int provided by user
     */
    private int getInt() {
        String s = null;
        int input = 0;
        while (input == 0) {
            try {
                s = br.readLine();
                input = Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("ERROR! PLEASE ENTER A POSITIVE INTEGER\n>>> ");
            }
        }
        return input;
    }

    /**
     * Helper function to display get name message.
     * 
     * @return: String - string provided by user.
     */
    private String getName() {
        System.out.print("\nPlease enter the tool name\n>>> ");
        return this.getString();
    }

    /**
     * Helper function to request a String from user until good input is received.
     * 
     * @return: String - string provided by user.
     */
    private String getString() {
        String s = null;
        boolean badInput = true;
        while (badInput) {
            try {
                s = br.readLine();
            } catch (IOException e) {
                System.out.print("ERROR! Please try again\n>>> ");
                continue;
            }
            badInput = false;
        }
        return s;
    }

    /**
     * Prints an ANSI escape code for clear screen on unix systems and simulates a
     * clear on windows
     */
    private void clear() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows") || osName.contains("windows")) {
            System.out.printf("%s", "\n".repeat(50));// cheap hack for windows OS'
        } else {
            System.out.print("\033[H\033[2J"); // ANSI escape code for clear Screen + home
        }
    }

    /**
     * Main app entry point to display menu and get user input
     */
    public void run() {
        this.clear();
        this.displayMenu();
        this.getMenuChoice();
    }

    public static void main(String args[]) {
        InvMgmt invMgmt = new InvMgmt();
        invMgmt.setInventory(DBReader.readTools());
        invMgmt.setProcurement(DBReader.readSuppliers());
        App app = new App(invMgmt);
        app.run();
    }
}
