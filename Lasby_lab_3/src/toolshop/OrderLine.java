package toolshop;

/**
 * Data container class to populate Orders and provide a low-coupled transfer
 * mechanism between inventory and procurement branches.
 * 
 * @author: Mike Lasby
 * @since: Oct. 11, 2020
 * @version: 1.0
 */

public class OrderLine {

    private String description;
    private int amount;
    private String supplier;
    private int supplierID;
    private int toolID;

    OrderLine(int toolID, String desc, int amt, int supplierID, String supplier) {
        this.toolID = toolID;
        this.description = desc;
        this.amount = amt;
        this.supplierID = supplierID;
        this.supplier = supplier;
    }

    /**
     * Add one to the this.amount
     */
    public void incAmount() {
        this.amount++;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getToolID() {
        return this.toolID;
    }

    public Object getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        String message = String.format("Item description: %s\nAmount ordered: %d\nSupplier: %s\n", description, amount,
                supplier);
        return message;
    }
}
