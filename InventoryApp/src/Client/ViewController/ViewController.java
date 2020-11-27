package Client.ViewController;

import java.util.HashMap;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import Client.ClientController.ClientServerConstants;
import org.json.JSONException;
import java.awt.event.*;
import Client.ClientController.*;
import Client.View.SubView;

/**
 * Base class for SubView Controllers
 * 
 * @author Mike Lasby and Tong Xu
 * @since Nov. 25, 2020
 * @version 1.1
 */

public abstract class ViewController implements ClientServerConstants {
    public ClientController clientCtrl;
    public SubView view;
    public ActionListener menuListener;

    ViewController() {
    }

    ViewController(ClientController clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    ViewController(ClientController clientCtrl, SubView view) {
        this.clientCtrl = clientCtrl;
        this.view = view;
    }

    protected void registerGuiMenuButton(String key) {
        view.registerGuiMenuButton(menuListener, key);
    }

    protected boolean areFieldsEmpty() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            if (fields.get(key).getText().equals("") | fields.get(key).getText().equals(" ")) {
                return true;
            }
        }
        return false;
    }

    protected void clearFields() {
        HashMap<String, JTextField> fields = view.getFields();
        for (String key : fields.keySet()) {
            fields.get(key).setText("");
        }
    }

    protected boolean isErrorMessage(Message response) {
        try {
            if (response.get(VERB).equals(ERROR)) {
                view.flashErrorMessage((String) response.get(DATA));
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class MyWindowStateListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent arg0) {
            Message exit;
            try {
                exit = new Message();
                exit.quitMessage();
                clientCtrl.sendMessage(exit);
                System.exit(0);
            } catch (JSONException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    protected abstract HashMap<String, JTextField> getInfoFields();

    protected abstract boolean areInfoFieldsEmpty();

    protected abstract void clearInfoFields();

    protected abstract void registerListeners();
}
