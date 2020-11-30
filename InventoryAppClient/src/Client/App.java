package Client;

import Client.ClientController.*;
import Client.View.*;
import Client.ViewController.*;

/**
 * Main entry point into Client side application
 * 
 * @author: Mike Lasby and Tong Xu
 * @since: November 24, 2020
 * @version: 1.0
 */
public class App {
    public static void main(String[] args) {
        Gui gui = new Gui();
        // String serverName = "50.99.198.77";
        String serverName = "localhost";
        CustomerView custView = new CustomerView(gui, "customerPanel");
        ClientController clientCtrl = new ClientController(serverName, 4444);
        CustomerController custCtrl = new CustomerController(custView, clientCtrl);
        InventoryView invView = new InventoryView(gui, "inventoryPanel");
        InventoryController invCtrl = new InventoryController(invView, clientCtrl);
        gui.display();
    }
}