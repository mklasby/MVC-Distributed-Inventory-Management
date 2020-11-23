package Client.ViewController;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Client.ClientController.*;

public abstract class ViewController {
    public ClientController clientCtrl;

    ViewController(ClientController clientCtrl) {
        this.clientCtrl = clientCtrl;
    }

}
