package ModelController;

import Client.ClientController.Message;
import DBController.CustomerDBController;

public class CustomerController extends ModelController {

	private CustomerDBController customerDB;
	
	public CustomerController(CustomerDBController customerDB) {
		this.customerDB = customerDB;
	}

	@Override
	public Message notify(Message data) {
		return null;
		
	}

	@Override
	public Message processMessage(Message data) {
		return null;
	}

}
