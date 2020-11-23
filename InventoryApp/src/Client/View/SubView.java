package Client.View;

public abstract class SubView {
    Gui gui;

    SubView(Gui gui) {
        this.gui = gui;
    }

    SubView() {

    }

    public abstract void display();
}
