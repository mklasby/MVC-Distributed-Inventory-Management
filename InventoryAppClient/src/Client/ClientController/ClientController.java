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

    public Message sendMessage(Message message) throws JSONException {
        try {
            if (message.getBoolean(QUIT)) {
                messageOut.writeObject(message.toString());
                System.out.println("MESSAGE SENT: " + message.toString());
                socket.close();
                System.exit(0);
            }
            messageOut.writeObject(message.toString());
            System.out.println("MESSAGE SENT: " + message.toString());
            String rawData = (String) messageIn.readObject();
            System.out.println("MESSAGE RECEIVED: " + rawData);
            return new Message(rawData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Message error = createErrorMessage(e);
            return error;
        }
    }

    private Message createErrorMessage(Exception e) {
        Message error = null;
        try {
            error = new Message(RESPONSE, ERROR, e.toString());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return error;
    }
}
