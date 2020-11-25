package ModelController;

import java.sql.ResultSet;
import org.json.JSONException;

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;

public class InventoryController extends ModelController implements ClientServerConstants {

	private InventoryDBController inventoryDB;
//	private InvMgmt model;
	
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
			Message response;
			switch(data.getString(VERB)) {
				case POST:
					addTool(data);
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

	private void addTool(Message data) {
		try {
			inventoryDB.addTool(data.getJSONObject(DATA));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	private Message searchTool(Message data) {
		try {
			String query = data.getString(QUERY);
			ResultSet rs = null;
			switch(query) {
				case BY_ID:
					rs = inventoryDB.searchToolbyID(data.getInt(DATA));
					break;
				case BY_NAME:
					rs = inventoryDB.searchToolbyName(data.getString(DATA));
					break;
				case BY_TYPE:
					rs = inventoryDB.searchToolbyType(data.getString(DATA));
					break;
				case ALL:
					rs = inventoryDB.getInventory();
			}
			Message response;
			if (rs == null) {
				String errorMessage = "Tool(s) not found!";
				response = new Message(RESPONSE, ERROR, errorMessage);
				return response;
			} else {
				// TODO: send ResultSet to Model to encode as JSONObject and return Message back to client
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
