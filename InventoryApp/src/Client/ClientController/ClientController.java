package Client.ClientController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientController implements ClientServerConstants {
    private Socket socket;
    private ObjectOutputStream messageOut;
    private ObjectInputStream messageIn;

    public ClientController() {

    }

    public ClientController(String serverName, int portNumber) {
        try {
            // TODO: Comment me out to run local
            // InetAddress serverIP = InetAddress.getByName(serverName);
            // gameSocket = new Socket(serverIP, portNumber);
            // TODO: Comment me out to run over IP
            socket = new Socket(serverName, portNumber);
            messageIn = new ObjectInputStream(socket.getInputStream());
            messageOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public JSONObject sendMessage(JSONObject message) {
        try {
            messageOut.writeObject(message);
            return (JSONObject) messageIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JSONObject error = createErrorJSON(e);
            return error;
        }
    }

    private JSONObject createErrorJSON(Exception e) {
        JSONObject error = new JSONObject();
        try {
            error.put("RESPONSE", "ERROR");
            error.put("DATA", e.getMessage().toString());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return error;
    }

    public JSONObject getMessage() {
        return null;
    }
}
