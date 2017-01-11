package online.eyeball.minecraft;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPUtil {

    public static final String SERVER_URL = "http://localhost:8080";

    public static void send(String computerName, String message) throws IOException {
        URL url = new URL(SERVER_URL + "/send");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        writer.write("{\"computerName\": \"" + computerName + "\", \"message\": \""+ message +"\"}\n");
        writer.close();
        connection.getResponseCode();
    }

}
