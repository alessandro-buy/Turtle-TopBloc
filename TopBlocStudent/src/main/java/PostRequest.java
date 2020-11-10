
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;

public class PostRequest {

    public static void main(String[] args) throws IOException, InterruptedException {

        String postEndpoint = "http://54.90.99.192:5000/challenge";

        String inputJson = "{ \"name\":\"tammy133\", \"salary\":\"5000\", \"age\":\"20\" }";
//        var request = HttpRequest.newBuilder()
//                .uri(URI.create(postEndpoint))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
//                .build();
//
//        var client = HttpClient.newHttpClient();
//        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.statusCode());
//        System.out.println(response.body());

        //URL url = new URL ("https://reqres.in/api/users");
        URL url = new URL(postEndpoint);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.setDoOutput(true);
        String jsonInputString = inputJson;

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }





    }
}