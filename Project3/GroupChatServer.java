import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GroupChatServer implements Runnable {
	// For reading messages from the keyboard
	private Socket readSocket;

	// For writing messages to the socket
	private ArrayList<PrintWriter> toClients;

	// Constructor sets the reader and writer for the child thread
	public GroupChatServer(Socket socket) {
		readSocket = socket;
		toClients = new ArrayList<PrintWriter>();
	}

	// The child thread starts here
	public void run() {
		try {
			BufferedReader fromSocketReader = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
			PrintWriter toSocketWriter = new PrintWriter(readSocket.getOutputStream(), true);
			toClients.add(toSocketWriter);

			// Keep doing till user types EOF (Ctrl-D)
			while (true) {
				// Read a line from the user
				String line = fromSocketReader.readLine();

				// If we get null, it means EOF, so quit
				if (line == null)
					break;

				// Write the line to the socket
				for (PrintWriter writer : toClients)
					writer.println(line);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
			System.exit(1);
		}

		// End the other thread too
		System.exit(0);
	}

	public static void main(String args[]) {
		// Server needs a port to listen on
		if (args.length != 1) {
			System.out.println("usage: java TwoWayAsyncMesgServer <port>");
			System.exit(1);
		}

		// Wait and connect to a client
		Socket cliSock = null;
		try {
			// Create a server socket with the given port
			ServerSocket servSock = new ServerSocket(Integer.parseInt(args[0]));
			// Wait for a client and accept it
			System.out.println("Waiting for a client ...");
			cliSock = servSock.accept();
			System.out.println("Connected to a client");
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// Set up a thread to read from user and send to client
		try {
			// Spawn a thread to read from user and write to socket
			Thread child = new Thread(new GroupChatServer(new Socket()));
			child.start();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// Now parent thread reads from client and display to user
		try {
			// Prepare to read from socket
			BufferedReader fromSockReader = new BufferedReader(new InputStreamReader(cliSock.getInputStream()));

			// Keep doing till server is done
			while (true) {
				// Read a line from the socket
				String line = fromSockReader.readLine();

				// If we get null, it means EOF
				if (line == null) {
					// Tell user client quit
					System.out.println("*** Client quit");
					break;
				}

				// Write the line to the user
				System.out.println("Client: " + line);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// End the other thread too
		System.exit(0);
	}
}
