package Client.ClientController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONObject;

public class ClientController {
    private Socket socket;
    private ObjectOutputStream messageOut;
    private ObjectInputStream messageIn;

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
            JSONObject error = createErrorJSON();
            return error;
        }
    }

    private JSONObject createErrorJSON() {
        JSONObject error = new JSONObject();
        error.put("type", "ERROR");
        return null;
    }
}

public class ClientGUI {

    private PrintWriter messageOut;

    private TicTacToeGUI gui;

    /**
     * Instantiates a new Client gui.
     *
     * @param serverName the server name
     * @param portNumber the port number
     */
    public ClientGUI(String serverName, int portNumber) {
        try {
            // TODO: Comment me out to run local
            InetAddress serverIP = InetAddress.getByName(serverName);
            gui = new TicTacToeGUI();
            gui.addButtonListener(new buttonListener());
            gui.addNameListener(new nameListener());
            gameSocket = new Socket(serverIP, portNumber);
            // TODO: Un comment to run local
            // gameSocket = new Socket(serverName, portNumber);
            messageIn = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
            messageOut = new PrintWriter(new OutputStreamWriter(gameSocket.getOutputStream()), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * Prompts user for input and writes response from Palindrome to stdout.
     */
    public void communicate() {
        String response = "";

        String mark = null;
        try {
            mark = messageIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gui.getPlayerField().setText(mark);
        while (true) {
            try {
                response = messageIn.readLine();
                if (response == null) {
                    continue;
                }
                if (response.equals("BOARD")) {
                    response = messageIn.readLine();
                    gui.updateBoard(response);
                    continue;
                }
                gui.getMessageBox().setText(response);
            } catch (IOException e) {
                System.out.println("Sending error: " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Connection closed, thanks for playing!");
                break;
            }
        }
        try {
            stdIn.close();
            messageOut.close();
            messageIn.close();
            gameSocket.close();
        } catch (IOException e) {
            System.out.println("Closing error: " + e.getMessage());
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        // TODO: Un Comment me to run local
        // ClientGUI aClient = new ClientGUI("localhost", 8099);
        // TODO: Comment me out to run local
        ClientGUI aClient = new ClientGUI("68.147.166.141", 8099);
        aClient.communicate();
    }

    /**
     * The type Name listener.
     */
    class nameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = gui.getNameField().getText();
            messageOut.println(name);
        }
    }

    /**
     * The type Button listener.
     */
    class buttonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int buttonPressed = -1;
            for (int i = 0; i < 9; i++) {
                if (event.getSource() == gui.getButtons().get(i)) {
                    buttonPressed = i;
                    break;
                }
            }
            int row = -1, col = -1;
            // Check which row was pressed
            if (buttonPressed <= 2) {
                row = 0;
            } else if (buttonPressed <= 5) {
                row = 1;
            } else if (buttonPressed <= 8) {
                row = 2;
            }

            // Check which column was pressed
            int a = 0, b = 3, c = 6;
            for (int i = 0; i < 3; i++) {
                if (buttonPressed == a || buttonPressed == b || buttonPressed == c) {
                    col = i;
                    break;
                }
                a++;
                b++;
                c++;
            }
            messageOut.println(row);
            messageOut.println(col);
        }
    }
}
