package toolshop;

/**
 * Container class for tool entities. Stores all required data for tool entity.
 * 
 * @author: Mike Lasby
 * @since: Oct. 9, 2020
 * @version 1.0
 */

public class Tool {
    private int toolID;
    private String description;
    private int quantity;
    private double price;
    private int lowSupply;
    private int targetSupply;
    private int supplierID;

    Tool(int toolID, String description, int quantity, double price, int supplierID) {
        this.toolID = toolID;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.supplierID = supplierID;
        this.targetSupply = 50; // we always order targetSupply - quantity when in low supply
        this.lowSupply = 40; // minimum stock of item before orderLine is generated
    }

    @Override
    public String toString() {
        return String.format("ToolID: %d, Description: %14s, Stock: %4d, Price: %6.2f, SupplierID: %5d\n", toolID,
                description, quantity, price, supplierID);
    }

    public int getToolID() {
        return this.toolID;
    }

    public void setToolID(int toolID) {
        this.toolID = toolID;
    }

    public void setTargetSupply(int targetSupply) {
        this.targetSupply = targetSupply;
    }

    public void setLowSupply(int lowSupply) {
        this.lowSupply = lowSupply;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setToolName(String toolName) {
        this.description = toolName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowSupply() {
        return this.lowSupply;
    }

    /**
     * Returns quantity string of tool.
     * 
     * @return: String - quantity string of tool
     */
    public String toQuantityString() {
        return String.format("%d of %s remaining in stock.\n", quantity, description);
    }

    public int getTargetSupply() {
        return this.targetSupply;
    }

    public int getSupplierID() {
        return this.supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

}
