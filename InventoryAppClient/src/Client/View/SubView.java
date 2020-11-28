package Client.View;

import Client.ViewController.ViewController.MyWindowStateListener;

import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Base class for JPanel window sub views
 * 
 * @author Mike Lasby && Tong Xu
 * @since Nov. 25, 2020
 * @version 1.1
 */
public abstract class SubView {
    public Gui gui;
    public JPanel mainPanel;
    public String mainPanelKey;
    public HashMap<String, JTextField> fields;
    public HashMap<String, JButton> buttons;
    public HashMap<String, JList> lists;
    public HashMap<String, JRadioButton> radioButtons;
    public HashMap<String, ButtonGroup> buttonGroups;
    public HashMap<String, JLabel> labels;
    public HashMap<String, JComboBox> comboBoxes;
    public HashMap<String, DefaultListModel> listModels;

    SubView(Gui gui, String mainPanelKey) {
        this.gui = gui;
        this.mainPanelKey = mainPanelKey;
    }

    SubView() {

    }

    public Gui getGui() {
        return this.gui;
    }

    public void setGui(Gui gui) {
        this.gui = gui;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public String getMainPanelKey() {
        return this.mainPanelKey;
    }

    public void setMainPanelKey(String mainPanelKey) {
        this.mainPanelKey = mainPanelKey;
    }

    public HashMap<String, JTextField> getFields() {
        return this.fields;
    }

    public void setFields(HashMap<String, JTextField> fields) {
        this.fields = fields;
    }

    public HashMap<String, JButton> getButtons() {
        return this.buttons;
    }

    public void setButtons(HashMap<String, JButton> buttons) {
        this.buttons = buttons;
    }

    public HashMap<String, JList> getLists() {
        return this.lists;
    }

    public void setLists(HashMap<String, JList> lists) {
        this.lists = lists;
    }

    public HashMap<String, JRadioButton> getRadioButtons() {
        return this.radioButtons;
    }

    public void setRadioButtons(HashMap<String, JRadioButton> radioButtons) {
        this.radioButtons = radioButtons;
    }

    public HashMap<String, ButtonGroup> getButtonGroups() {
        return this.buttonGroups;
    }

    public void setButtonGroups(HashMap<String, ButtonGroup> buttonGroups) {
        this.buttonGroups = buttonGroups;
    }

    public HashMap<String, JLabel> getLabels() {
        return this.labels;
    }

    public void setLabels(HashMap<String, JLabel> labels) {
        this.labels = labels;
    }

    public HashMap<String, JComboBox> getComboBoxes() {
        return this.comboBoxes;
    }

    public void setComboBoxes(HashMap<String, JComboBox> comboBoxes) {
        this.comboBoxes = comboBoxes;
    }

    public void display() {
        gui.setPanel(mainPanelKey);
    }

    public void flashErrorMessage(String error) {
        JOptionPane.showMessageDialog(mainPanel, error, "ERROR!", JOptionPane.ERROR_MESSAGE);
    }

    public void flashSuccessMessage(String success) {
        JOptionPane.showMessageDialog(mainPanel, success, "Success!", JOptionPane.PLAIN_MESSAGE);
    }

    public HashMap<String, DefaultListModel> getListModels() {
        return this.listModels;
    }

    public void setListModels(HashMap<String, DefaultListModel> listModels) {
        this.listModels = listModels;
    }

    public String getFieldText(String fieldName) {
        return fields.get(fieldName).getText();
    }

    public JTextField getField(String fieldName) {
        for (String key : fields.keySet()) {
            if (key.equals(fieldName)) {
                return fields.get(key);
            }
        }
        return null;
    }

    public JComboBox getComboBox(String key) {
        return comboBoxes.get(key);
    }

    public void registerWindowClosingListener(MyWindowStateListener listener) {
        gui.registerExitButton(listener);
    }

    public void registerButtonListener(ActionListener listener) {
        for (String key : buttons.keySet()) {
            registerButtonActionListener(listener, buttons.get(key));
        }
    }

    public void registerRadioButtonListener(ActionListener listener) {
        for (String key : radioButtons.keySet()) {
            registerButtonActionListener(listener, radioButtons.get(key));
        }
    }

    public void registerComboBoxListeners(ActionListener listener) {
        for (String key : comboBoxes.keySet()) {
            comboBoxes.get(key).addActionListener(listener);
        }
    }

    public void registerButtonActionListener(ActionListener listener, AbstractButton button) {
        button.addActionListener(listener);
    }

    public void registerListListener(ListSelectionListener listener) {
        for (String key : lists.keySet()) {
            lists.get(key).addListSelectionListener(listener);
        }
    }

    public void registerGuiMenuButton(ActionListener menuListener, String key) {
        JButton target = gui.getButton(key);
        target.addActionListener(menuListener);
    }

    public void clearFields() {
        for (String key : fields.keySet()) {
            fields.get(key).setText("");
        }
    }

    public SubView(HashMap<String, JTextField> fields) {
        this.fields = fields;
    }

    // Use this method to build main pasnel
    protected abstract void buildMainPanel();

    // Use these methods to register each sub views components with SubView
    // container HashMaps, don't forget to init container
    protected abstract void registerFields();

    protected abstract void registerButtons();

    protected abstract void registerRadioButtons();

    protected abstract void registerButtonGroups();

    protected abstract void registerLists();

    protected abstract void registerLabels();

    protected abstract void registerComboBoxes();
}