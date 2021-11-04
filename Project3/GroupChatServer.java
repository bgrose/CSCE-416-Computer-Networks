import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Group Chat Server Code Bradley Grose 11/1/2021
 */
public class GroupChatServer implements Runnable {
	// For reading messages from the keyboard
	private Socket readSocket;

	// For writing messages to the socket
	private static List<PrintWriter> toClients = new ArrayList<PrintWriter>();

	/**
	 * Constructor for Server
	 * @param socket Socket to read the message
	 */
	public GroupChatServer(Socket socket) {
		readSocket = socket;
	}

	/**
	 * Adds Print Writer Client to the list
	 * @param pw PrintWriter to write to the socket for Client
	 */
	public static synchronized boolean addClient(PrintWriter pw) {
		return toClients.add(pw);
	}

	/**
	 * Removes Print Writer Client from the list
	 * @param pw PrintWriter to write to the socket for Client
	 */
	public static synchronized boolean removeClient(PrintWriter pw) {
		return (toClients.remove(pw));
	}

	/**
	 * Sends message to all clients except the one who sent it
	 * @param msg Message to send to all clients
	 * @param author Author of the message
	 */
	public static synchronized void relayMessage(String msg, PrintWriter author) {
		for (PrintWriter pw : toClients) {
			if (pw != author)
				pw.println(msg);
		}
	}

	/**
	 * This method is called when the thread is started.
	 */
	public void run() {
		try {
			BufferedReader fromSocketReader = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
			PrintWriter toSocketWriter = new PrintWriter(readSocket.getOutputStream(), true);
			addClient(toSocketWriter);

			// Keep doing till user types EOF (Ctrl-D)
			while (true) {
				// Read a line from the user
				String line = fromSocketReader.readLine();

				System.out.println("Received: " + line);

				if (line == null || line == "^c") {
					System.out.println("Client disconnected");
					break;
				}

				System.out.println("Relaying message to all clients");
				relayMessage(line, toSocketWriter);
			}
			System.out.println("Removing client");
			removeClient(toSocketWriter);
			System.out.println("Closing socket writer");
			toSocketWriter.close();
			System.out.println("Closing socket reader");
			fromSocketReader.close();

		} catch (Exception e) {
			System.out.println("Calling Error " + e);
			System.exit(1);
		}
	}

	/**
	 * This method is called when the server is started.
	 * @input args[0] is the port number
	 */
	public static void main(String args[]) {
		// Server needs a port to listen on
		if (args.length != 1) {
			System.out.println("usage: java TwoWayAsyncMesgServer <port>");
			System.exit(1);
		}

		try {
			// Create a server socket with the given port
			ServerSocket servSock = new ServerSocket(Integer.parseInt(args[0]));
			while (true) {
				// Wait to accept another client
				Socket sock = servSock.accept();
				// Create a new thread for the client
				Thread t = new Thread(new GroupChatServer(sock));
				t.start();

			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
