package InventoryModel;

import java.sql.ResultSet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Data container class for Suppliers.
 * 
 * @author: Mike Lasby
 * @since: Oct. 10, 2020
 * @version: 1.0
 */

public class Supplier extends JSONObject {
    private int supplierID;
    private String name;
    private String type;
    private String address;
    private String cName;
    private String phone;

    public Supplier(ResultSet supplier) {
        // TODO:
        super();
    }

    public Supplier(int supplierID, String name, String type, String address, String cName, String phone) {
        this.setSupplierID(supplierID);
        this.setName(name);
        this.setAddress(address);
        this.setCName(cName);
        this.putFields();
    }

    public String toDescriptionString() {
        return String.format("SupplierID: %4d, Company Name: %20s, Address: %20s, Sales Contact: %20s\n", supplierID,
                name, address, cName);
    }

    public void putFields() {
        try {
            this.put("SupplierID", supplierID);
            this.put("Name", name);
            this.put("Type", type);
            this.put("Address", address);
            this.put("CName", cName);
            this.put("Phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getSupplierID() {
        return this.supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCName() {
        return this.cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public JSONObject encode() {
        return this;
    }

}