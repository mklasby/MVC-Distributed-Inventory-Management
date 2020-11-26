package Client.View;

import java.util.HashMap;
import javax.swing.*;

public abstract class SubView {
    Gui gui;

    SubView(Gui gui) {
        this.gui = gui;
    }

    SubView() {

    }

    public abstract void display();

    public abstract void flashErrorMessage(String string);

    public abstract void flashSuccessMessage(String success);

    public abstract HashMap<String, JTextField> getFields();

    public abstract DefaultListModel<String> getListModel();

    public abstract JTextField getField(String fieldName);
}
