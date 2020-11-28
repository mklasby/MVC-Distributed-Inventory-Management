package ModelController;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;

import Common.Message;
import Common.ClientServerConstants;
import DBController.CustomerDBController;
import CustomerModel.CustomerMgmt;

public class CustomerController extends ModelController implements ClientServerConstants {

	private CustomerDBController customerDB;
	private CustomerMgmt model;

	public CustomerController(CustomerDBController customerDB) {
		this.customerDB = customerDB;
		model = new CustomerMgmt();
	}

	@Override
	public Message notify(Message data) {
		try {
			if (!data.getString(DB).equals(CUSTOMER))
				return null;
			if (data.getString(MESSAGE_TYPE).equals(REQUEST)) {
				return processMessage(data);
			}
		} catch (JSONException jse) {
			jse.printStackTrace();
		}
		return null;

	}

	@Override
	public Message processMessage(Message data) {
		try {
			Message response = null;
			switch (data.getString(VERB)) {
				case POST:
					response = addCustomer(data);
					break;
				case PUT:
					response = modifyCustomerInfo(data);
					break;
				case DELETE:
					response = deleteCustomer(data);
					break;
				case GET:
					response = searchCustomer(data);
					break;
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Message deleteCustomer(Message data) {
		Message response = null;
		try {
			customerDB.removeCustomer(data.getJSONObject(DATA).getInt("ClientID"));
			String successMessage = "Customer deleted successfully.";
			response = new Message(RESPONSE, OK, successMessage);
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}

	private Message modifyCustomerInfo(Message data) {
		Message response = null;
		try {
			customerDB.modifyInfo(data.getJSONObject(DATA));
			String successMessage = "Customer updated successfully.";
			response = new Message(RESPONSE, OK, successMessage);
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}

	private Message searchCustomer(Message data) {
		try {
			String query = data.getString(QUERY);
			ResultSet rs = null;
			switch (query) {
				case BY_ID:
					rs = customerDB.searchByID(Integer.parseInt(data.getString(DATA)));
					break;
				case BY_NAME:
					rs = customerDB.searchByName(data.getString(DATA));
					break;
				case BY_TYPE:
					rs = customerDB.searchByType(data.getString(DATA));
					break;
				case ALL:
					rs = customerDB.getAllCustomers();
			}
			Message response;
			if (rs == null) {
				String errorMessage = "Customer(s) not found!";
				response = new Message(RESPONSE, ERROR, errorMessage);
			} else {
				response = new Message(RESPONSE, OK, model.encodeCustomerSearchQuery(rs));
			}
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Message addCustomer(Message data) {
		Message response = null;
		try {
			try {
				customerDB.addCustomer(data.getJSONObject(DATA));
				String successMessage = "Customer added successfully.";
				response = new Message(RESPONSE, OK, successMessage);
			} catch (SQLException sqlE) {
				String errorMessage = "Invalid ClientID, use " + customerDB.generateNewID() + ".";
				response = new Message(RESPONSE, ERROR, errorMessage);
			}
		} catch (JSONException jsonE) {
			jsonE.printStackTrace();
		}
		return response;
	}

}
