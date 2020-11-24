package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Client.View.*;
import Client.ClientController.*;

public class CustomerController extends ViewController {
    public CustomerView view;
    private String searchBy;

    CustomerController(CustomerView view, ClientController clientCtrl) {
        super(clientCtrl);
        this.view = view;
        registerGuiMenu();
        view.registerButtons(new ButtonListener());
    }

    @Override
    protected void registerGuiMenu() {
        this.view.registerGuiMenu(new MenuListener());
    }

    public JsonObject searchCustomer(ActionEvent e) {
        return null;
    }

    public class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.display();
        }
    }

    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "search") {
                searchCustomer(e);
            } else if (e.getActionCommand() == "clearSearch"){
                clearSearch(e);
            } else if (e.getActionCommand() == "update"){
                updateRecord(e);
            } else if (e.getActionCommand() == "delete"){
                deleteRecord(e);
            } else if (e.getActionCommand() == "clear"){
                view.clearInfoFields(); 
            }

            }view.display();
    }

    public class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearResults();
            searchBy = e.getActionCommand();

        }
    }

}
