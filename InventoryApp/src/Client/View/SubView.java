package Client.View;

import javax.swing.JOptionPane;

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
}
