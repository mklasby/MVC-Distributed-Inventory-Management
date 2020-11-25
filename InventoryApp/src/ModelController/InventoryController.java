package ModelController;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;
import InventoryModel.Inventory;

public class InventoryController extends ModelController implements ClientServerConstants {

	private InventoryDBController inventoryDB;
	private Inventory model;
	
	public InventoryController(InventoryDBController inventoryDB) {
		this.inventoryDB = inventoryDB;
		model = new Inventory();
	}

	@Override
	public Message notify(Message data) {
		try {
			if (! data.getString(DB).equals(INVENTORY)) return null;
			if (data.getString(MESSAGE_TYPE).equals(REQUEST)) {
				return processMessage(data);
			}
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
		return null;
	}

	public Message processMessage(Message data) {
		try {
			Message response = null;
			switch(data.getString(VERB)) {
				case POST:
					response = addTool(data);
					break;
				case GET:
					response = searchTool(data);
					break;
				case PUT:
				case DELETE:
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Message addTool(Message data) {
		Message response = null;
		try {
			try {
				inventoryDB.addTool(data.getJSONObject(DATA));
				String successMessage = "Tool added successfully.";
				response = new Message(RESPONSE, OK, successMessage);
			} catch (SQLException sqlE) {
				String errorMessage = "Invalid ToolID, use " + inventoryDB.generateNewID() + ".";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} 
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
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
