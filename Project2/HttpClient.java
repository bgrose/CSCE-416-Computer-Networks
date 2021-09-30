package Project2;

import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;

/**
 * Bradley Grose Project 2 9/18/2021
 */

public class HttpClient {

    public static void getHeadAndBody(HttpURLConnection connection, BufferedWriter writer) throws Exception {

        System.out.println("Here1");
        InputStream input = connection.getInputStream();

        // Get All Header Fields
        for (int i = 0; i < connection.getHeaderFields().size(); i++) {
            System.out.println(connection.getHeaderField(i));
        }

        // Print body to writer using documentation commands
        int c;
        while ((c = input.read()) != -1) {
            writer.write((char) c);
        }
    }

    public static void main(String[] args) {
        try {
            URL url = new URL(args[0]);
            BufferedWriter writer = new BufferedWriter(new FileWriter("HttpClientOutput.txt"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check for redirect
            if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
                System.out.println("Temporarily Redirected to: " + connection.getHeaderField("Location"));
                writer.write("Temporarily Redirected to: " + connection.getHeaderField("Location"));
                connection = (HttpURLConnection) new URL(connection.getHeaderField("Location")).openConnection();
                getHeadAndBody(connection, writer);
            } else if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM) {
                System.out.println("Permanently Redirected to: " + connection.getHeaderField("Location"));
                writer.write("Permanently Redirected to: " + connection.getHeaderField("Location"));
                connection = (HttpURLConnection) new URL(connection.getHeaderField("Location")).openConnection();
                getHeadAndBody(connection, writer);
            } else if (connection.getResponseCode() == HttpURLConnection.HTTP_SEE_OTHER) {
                System.out.println("See Other: " + connection.getHeaderField("Location"));
                writer.write("See Other: " + connection.getHeaderField("Location"));
                connection = (HttpURLConnection) new URL(connection.getHeaderField("Location")).openConnection();
                getHeadAndBody(connection, writer);
            } else {
                System.out.println("Here");
                getHeadAndBody(connection, writer);
            }
            writer.close();
        }

        catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}
