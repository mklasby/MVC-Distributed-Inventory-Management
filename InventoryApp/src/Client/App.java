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
        ClientController clientCtrl = new ClientController("localhost", 4444);
        CustomerView custView = new CustomerView(gui);
        CustomerController custCtrl = new CustomerController(custView, clientCtrl);
        InventoryView invView = new InventoryView(gui);
        InventoryController invCtrl = new InventoryController(invView, clientCtrl);
        gui.display();
    }
}