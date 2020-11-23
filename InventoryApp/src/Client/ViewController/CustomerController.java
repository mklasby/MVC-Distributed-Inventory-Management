package Client.ViewController;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Client.View.*;
import Client.ClientController.*;

public class CustomerController extends ViewController {
    CustomerView view;

    CustomerController(CustomerView view, ClientController clientCtrl) {
        super(clientCtrl);
        this.view = view;
    }

    // TODO: Could it be combined into a single listener in parent?
    private class CustomerListener implements ActionListener {
        public void actionPerfomed(ActionEvent e) {
            this.view.display();
        }
    }

}
