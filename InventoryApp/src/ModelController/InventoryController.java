package ModelController;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;
import InventoryModel.InvMgmt;

public class InventoryController extends ModelController implements ClientServerConstants {

	private InventoryDBController inventoryDB;
	private InvMgmt model;
	
	public InventoryController(InventoryDBController inventoryDB) {
		this.inventoryDB = inventoryDB;
		model = new InvMgmt();
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
				case GET:
					response = searchTool(data);
					break;
				case PUT:
					response = makeSale(data);
					break;
				case POST:
					response = addTool(data);
					break;
				case DELETE:
					response = deleteTool(data);
					break;
				case ORDER:
					response = generateOrder();
					break;
			}
			return response;
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
					rs = inventoryDB.searchToolbyID(Integer.parseInt(data.getString(DATA)));
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
			} else {
				response = new Message(RESPONSE, OK, model.encodeToolSearchQuery(rs));
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Message makeSale(Message data) {
		Message response = null;
		try {
			try {
				// make sale
				JSONObject tool = data.getJSONObject(DATA);
				int id = tool.getInt("ToolID");
				int qtyInStock = tool.getInt("ToolID");
				inventoryDB.reduceToolQuantity(id, qtyInStock);
				String successMessage = "Quantity updated successfully.";
				response = new Message(RESPONSE, OK, successMessage);
				
				// check quantity and generate orderline and order if low in stock
				if (qtyInStock < 40) {
					createOrderLine(tool);

				}
			} catch (SQLException sqlE) {
				String errorMessage = "Exceeded quantity in stock. Please try with smaller quantity.";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} 
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}

	private void createOrderLine(JSONObject data) {
		try {
			ResultSet hasOrderline = inventoryDB.hasOrderLine(data.getInt("ToolID"));
			JSONObject orderline;
			if (hasOrderline == null) {
				orderline = model.restock(data, hasOrderline);
				inventoryDB.generateOrderLine(orderline);
			} else {
				orderline = model.incrementOrderLine(data, hasOrderline);
				inventoryDB.updateOrderLine(orderline);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Message generateOrder() {
		ResultSet order = inventoryDB.returnOrder();
		// TODO
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


	private Message deleteTool(Message data) {
		Message response = null;
		try {
			inventoryDB.deleteToolbyID(data.getJSONObject(DATA).getInt("ToolId"));
			String successMessage = "Tool deleted successfully.";
			response = new Message(RESPONSE, OK, successMessage);
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}
	
}
