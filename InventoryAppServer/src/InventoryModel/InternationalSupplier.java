package InventoryModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Data container class for International Suppliers.
 * 
 * @author: Mike Lasby
 * @since: Oct. 10, 2020
 * @version: 1.0
 */
public class InternationalSupplier extends Supplier {

    private String type;
    private double importTax;

    public InternationalSupplier(int supplierID, String name, String address, String cName, String phone,
            double importTax) {
        super(supplierID, name, address, cName, phone);
        setType("International");
        setImportTax(importTax);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImportTax(double tax) {
        this.importTax = tax;
    }

    public double getImportTax() {
        return this.importTax;
    }

    @Override
    public void putFields() {
        try {
            super.putFields();
            put("Type", type);
            put("ImportTax", importTax);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject encode() {
        super.putFields();
        putFields();
        return this;
    }

    public String toDescriptionString() {
        // TODO: Add type info?
        return super.toDescriptionString();
    }
}
