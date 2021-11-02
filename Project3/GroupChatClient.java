import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is used to create a client that can connect to a server.
 */
public class GroupChatClient implements Runnable {

	private BufferedReader fromUserReader;
	private PrintWriter toSockWriter;
	private static String name;

	/**
	 * This method is used to create a client that can connect to a server.
	 * @param reader reader for client socket
	 * @param writer write to server
	 */
	public GroupChatClient(BufferedReader reader, PrintWriter writer) {
		fromUserReader = reader;
		toSockWriter = writer;
	}

	/**
	 * This method is used to run the client.
	 */
	public void run() {
		try {
			while (true) {
				String line = fromUserReader.readLine();

				if (line == null)
					break;

				toSockWriter.println(name + " : " + line);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		System.exit(0);
	}

	/**
	 * This method is used to create a client that can connect to a server.
	 * @param args[0] host name
	 * @param args[1] port number
	 * @param args[2] username
	 */
	public static void main(String args[]) {
		if (args.length != 3) {
			System.out.println("usage: java TwoWayAsyncMesgClient <host> <port> <name>");
			System.exit(1);
		}

		// Connect to the server at the given host and port
		Socket sock = null;
		try {
			sock = new Socket(args[0], Integer.parseInt(args[1]));
			System.out.println("Connected to server at " + args[0] + ":" + args[1]);
			name = args[2];
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// Set up a thread to read from user and write to socket
		try {
			// Prepare to write to socket with auto flush on
			PrintWriter toSockWriter = new PrintWriter(sock.getOutputStream(), true);
			// Prepare to read from keyboard
			BufferedReader fromUserReader = new BufferedReader(new InputStreamReader(System.in));

			// Spawn a thread to read from user and write to socket
			Thread child = new Thread(new GroupChatClient(fromUserReader, toSockWriter));
			child.start();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// Now read from socket and display to user
		try {
			// Prepare to read from socket
			BufferedReader fromSockReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			// Keep doing till server is done
			while (true) {
				// Read a line from the socket
				String line = fromSockReader.readLine();

				// If we get null, it means EOF
				if (line == null) {
					// Tell user server quit
					System.out.println("*** Server quit");
					break;
				}

				// Write the line to the user
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// End the other thread too
		System.exit(0);
	}
}