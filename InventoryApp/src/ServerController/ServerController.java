package ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Client.ClientController.Message;
import Client.ClientController.ClientServerConstants;
import DBController.InventoryDBController;
import DBController.CustomerDBController;
import ModelController.ModelController;
import ModelController.InventoryController;
import ModelController.CustomerController;

public class ServerController implements Runnable, ClientServerConstants {

	private ObjectOutputStream messageOut;
	private ObjectInputStream messageIn;
	private ArrayList<ModelController> models;

	public ServerController(Socket socket) {
		try {
			messageOut = new ObjectOutputStream(socket.getOutputStream());
			messageIn = new ObjectInputStream(socket.getInputStream());
			models = new ArrayList<>();
			
			InventoryController inv = new InventoryController(new InventoryDBController());
			registerModel(inv);
			CustomerController customer = new CustomerController(new CustomerDBController());
			registerModel(customer);

		} catch (IOException ioe) {
			ioe.getStackTrace();
		}
	}

	public void registerModel(ModelController model) {
		models.add(model);
	}

	@Override
	public void run() {
		while (true) {
			try {
				String rawData = (String) messageIn.readObject();
				System.out.println(rawData);
				Message data = new Message(rawData);
				System.out.print(data.toString());

				if (data.getBoolean(QUIT))
					break;
				for (ModelController model : models) {
					model.notify(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


}
