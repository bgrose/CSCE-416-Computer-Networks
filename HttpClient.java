import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;

/**
 * Bradley Grose
 * Project 2
 * 9/18/2021
 */
public class HttpClient {
    public static void main(String[] args) {
        try{
            URL url = new URL(args[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream input = connection.getInputStream();
            
            //Print Header
            String header = connection.getHeaderField(0);
            System.out.println(header);
            
            //Print Body
            int c;
            while((c = input.read()) != -1){
                System.out.print((char)c);
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
        