package Client.ViewController;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import Client.ClientController.ClientServerConstants;
import org.json.JSONException;
import java.awt.event.*;
import Client.ClientController.*;
import Client.View.SubView_v1;

/**
 * Base class for SubView Controllers
 * 
 * @author Mike Lasby and Tong Xu
 * @since Nov. 25, 2020
 * @version 1.1
 */

public abstract class ViewController_v1 implements ClientServerConstants {
    public ClientController clientCtrl;
    public SubView_v1 view;
    public ActionListener menuListener;

    ViewController_v1() {
    }

    ViewController_v1(ClientController clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

    ViewController_v1(ClientController clientCtrl, SubView_v1 view) {
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

    protected void isErrorMessage(Message response) {
        try {
            if (response.get(VERB).equals(ERROR)) {
                view.flashErrorMessage((String) response.get(DATA));
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected abstract HashMap<String, JTextField> getInfoFields();

    protected abstract boolean areInfoFieldsEmpty();

    protected abstract void clearInfoFields();

    protected abstract void registerListeners();
}
