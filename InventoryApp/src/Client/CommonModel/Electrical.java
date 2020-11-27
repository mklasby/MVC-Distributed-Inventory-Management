package Client.CommonModel;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Electrical extends Tool {

	private String toolType, powerType;
	
	public Electrical(int toolID, String name, String type, int qty, double price, int supplierID, String powerType) {
		super(toolID, name, qty, price, supplierID);
		setToolType(type);
		setPowerType(powerType);
	}

	public Electrical (JSONObject jsonTool) throws JSONException {
        toolId = jsonTool.getInt("ToolID");
        name = jsonTool.getString("Name");
        qty = jsonTool.getInt("Quantity");
        price = jsonTool.getDouble("Price");
        supplierID = jsonTool.getInt("SupplierID");
        toolType = jsonTool.getString("Type");
        powerType = jsonTool.getString("PowerType");
	}
	
    public String toDescriptionString() {
        return super.toDescriptionString() + String.format(", Type: %-15s, PowerType: %-6s", toolType, powerType); 
    }
    
    @Override 
    public void putFields() {
		try {
			super.putFields();
			put("Type", toolType);
			put("PowerType", powerType);
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

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

}
