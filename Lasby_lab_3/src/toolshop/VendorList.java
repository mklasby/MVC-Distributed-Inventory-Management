package toolshop;

/**
 * Collection of Supplier objects
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0 
 */

import java.util.ArrayList;

class VendorList {
    private ArrayList<Supplier> vendors;

    public VendorList(ArrayList<Supplier> suppliers) {
        this.setVendors(suppliers);
    }

    @Override
    public String toString() {
        String message = "";
        for (Supplier s : vendors) {
            message.concat(s.toString());
        }
        return message;
    }

    public ArrayList<Supplier> getVendors() {
        return vendors;
    }

    public void setVendors(ArrayList<Supplier> vendors) {
        this.vendors = vendors;
    }

    /**
     * Get supplier by id
     * 
     * @param id: int - SupplierID to get
     * @return: Supplier - supplier matching id or null if no match
     */
    public Supplier getVendor(int id) {
        Supplier foundSupplier = null;
        for (Supplier s : vendors) {
            if (s.getSupplierID() == id) {
                foundSupplier = s;
                break;
            }
        }
        return foundSupplier;
    }

    /**
     * Get supplier name by id
     * 
     * @param supplierID: int - id to get name of
     * @return: String - supplier.companyName or no match string
     */
    public String getNameByID(int supplierID) {
        Supplier thisSupplier = this.getVendor(supplierID);
        if (thisSupplier == null) {
            return String.format("No supply found with ID %d", supplierID);
        } else {
            return thisSupplier.getCompanyName();
        }
    }
}