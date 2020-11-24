package ModelController;

import Client.ClientController.Message;

import java.sql.ResultSet;

import org.json.JSONException;

import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;

public class InventoryController extends ModelController implements ClientServerConstants {

	private InventoryDBController inventoryDB;
	
	public InventoryController(InventoryDBController inventoryDB) {
		this.inventoryDB = inventoryDB;
	}

	@Override
	public Message notify(Message data) {
		try {
			if (data.getString(MESSAGE_TYPE).equals(REQUEST)) {
				processMessage(data);
			}
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
		return null;
	}

	public Message processMessage(Message data) {
		try {
			switch(data.getString(VERB)) {
				case POST:
					inventoryDB.addTool(data.getJSONObject(DATA));
					break;
				case GET:
					searchTool(data);
					break;
				case PUT:
				case DELETE:
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Message searchTool(Message data) {
		try {
			String query = data.getString(QUERY);
			ResultSet rs = null;
			switch(query) {
				case BY_ID:
					rs = inventoryDB.searchToolbyID(data.getJSONObject(DATA).getInt("ToolID"));
					break;
				case BY_NAME:
					rs = inventoryDB.searchToolbyName(data.getJSONObject(DATA).getString("Name"));
					break;
				case BY_TYPE:
					rs = inventoryDB.searchToolbyType(data.getJSONObject(DATA).getString("Type"));
					break;
				case ALL:
					rs = inventoryDB.getInventory();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
