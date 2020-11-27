package ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	/** Socket for receiving incoming connections. */
	private ServerSocket serverSocket;

	/** thread pool */
	private ExecutorService pool;

	/**
	 * Create a Server that listens for connection on port 4444.
	 */
	public Server() {
		try {
			serverSocket = new ServerSocket(4444);
			pool = Executors.newCachedThreadPool();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void serve() {
		try {
			System.out.println("Server is running...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client Connected");
				pool.execute(new ServerController(clientSocket));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		pool.shutdown();

	}

	public static void main(String[] args) {
		Server myServer = new Server();
		myServer.serve();
	}
}
