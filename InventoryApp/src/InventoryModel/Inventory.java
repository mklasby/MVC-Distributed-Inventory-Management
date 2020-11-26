package InventoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

public class Inventory {

	public JSONArray encodeSearchQuery(ResultSet rs) {
		try {
			JSONArray jsonArray = new JSONArray();
			do {
				int toolID = rs.getInt("ToolID");
				String name = rs.getString("Name");
				String type = rs.getString("Type");
				int qty = rs.getInt("Quantity");
				double price = rs.getDouble("Price");
				int supplierID = rs.getInt("SupplierID");

				if (type.equalsIgnoreCase("electrical")) {
					String powerType = rs.getString("PowerType");
					Electrical electrical = new Electrical(toolID, name, type, qty, price, supplierID, powerType);
					jsonArray.put((JSONObject) electrical.encode());
				} else {
					Tool tool = new Tool(toolID, name, type, qty, price, supplierID);
					jsonArray.put((JSONObject) tool.encode());
				}
			} while (rs.next());
			return jsonArray;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
