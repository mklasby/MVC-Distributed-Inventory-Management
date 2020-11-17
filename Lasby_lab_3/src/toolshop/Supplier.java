package toolshop;

/**
 * Data container class for Suppliers.
 * 
 * @author: Mike Lasby
 * @since: Oct. 10, 2020
 * @version: 1.0
 */

public class Supplier {
    private int supplierID;
    private String companyName;
    private String address;
    private String salesContact;

    public Supplier(int supplierID, String companyName, String address, String salesContact) {
        this.setSupplierID(supplierID);
        this.setCompanyName(companyName);
        this.setAddress(address);
        this.setSalesContact(salesContact);
    }

    @Override
    public String toString() {
        return String.format("SupplierID: %4d, Company Name: %20s, Address: %20s, Sales Contact: %20s\n", supplierID,
                companyName, address, salesContact);
    }

    public String getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(String salesContact) {
        this.salesContact = salesContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

}
