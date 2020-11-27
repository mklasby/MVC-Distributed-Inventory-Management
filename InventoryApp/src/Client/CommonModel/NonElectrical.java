package Client.CommonModel;

import org.json.JSONException;
import org.json.JSONObject;

public class NonElectrical extends Tool {

	private String toolType;
	
	public NonElectrical(int toolID, String name, String type, int qty, double price, int supplierID) {
		super(toolID, name, qty, price, supplierID);
		setToolType(type);
	}

	public NonElectrical (JSONObject jsonTool) throws JSONException {
        toolId = jsonTool.getInt("ToolID");
        name = jsonTool.getString("Name");
        qty = jsonTool.getInt("Quantity");
        price = jsonTool.getDouble("Price");
        supplierID = jsonTool.getInt("SupplierID");
        toolType = jsonTool.getString("Type");
	}
	
    public String toDescriptionString() {
        return super.toDescriptionString() + String.format(", Type: %-15s", toolType); 
    }
    
    @Override 
    public void putFields() {
		try {
			super.putFields();
			put("Type", toolType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
	
	@Override
	public JSONObject encode() {
		putFields();
		return this;
	}

	public String getToolType() {
		return toolType;
	}

	public void setToolType(String toolType) {
		this.toolType = toolType;
	}

}
