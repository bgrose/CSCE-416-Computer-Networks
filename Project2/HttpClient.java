package Project2;
//TODO REMOVE PROJECT2  

/*
 * HTTP Client Reciever from command line
 * Bradley Grose for Project 2
*/

// Package for I/O related stuff
import java.io.*;

// Package for socket related stuff
import java.net.*;

// Package for object related stuff
import java.util.*;

public class HttpClient {

	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("URL input required");
		} else {
			try {
				URL url = new URL(args[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				InputStream in = connection.getInputStream();
				Map<String, List<String>> headers = connection.getHeaderFields();
				for (String key : headers.keySet()) {
					System.out.println(key + ": " + headers.get(key));
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e);
			}
		}
	}
}
