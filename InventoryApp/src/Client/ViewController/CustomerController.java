package Client.ViewController;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Client.View.*;
import Client.ClientController.*;
import org.json.JSONObject;

public class CustomerController extends ViewController {
    public CustomerView view;
    private String searchBy;

    public CustomerController(CustomerView view, ClientController clientCtrl) {
        super(clientCtrl);
        this.view = view;
        registerGuiMenu();
        registerButtons();
        registerComboBox();
    }

    private void registerComboBox() {
        view.registerComboBox(new ComboBoxListener());
    }

    private void registerButtons() {
        view.registerButtons(new ButtonListener());
    }

    @Override
    protected void registerGuiMenu() {
        this.view.registerGuiMenu(new MenuListener());
    }

    public void searchCustomer(ActionEvent e) {
        return;
    }

    public void clearSearch(ActionEvent e) {
        view.clearSearch();
    }

    public void deleteRecord(ActionEvent e) {
    }

    public void updateRecord(ActionEvent e) {
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
            JButton button = (JButton) e.getSource();
            String text = button.getText();
            System.out.print(text);
            String cmd = e.getActionCommand();
            System.out.print(cmd);

            if (e.getActionCommand() == "search") {
                searchCustomer(e);
            } else if (e.getActionCommand() == "clearSearch") {
                System.out.println("clear search!");
                clearSearch(e);
            } else if (e.getActionCommand() == "update") {
                updateRecord(e);
            } else if (e.getActionCommand() == "delete") {
                deleteRecord(e);
            } else if (e.getActionCommand() == "clear") {
                view.clearInfoFields();
            }
        }
    }

    public class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearResults();
            searchBy = e.getActionCommand();
        }
    }
}
