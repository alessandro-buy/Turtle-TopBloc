import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.HashMap;
import java.net.URLEncoder;
import java.io.*;




public class JavaRestClientTest {



    public static void main(String[] args) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL("https://www.youtube.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        Map<String, String> params = new HashMap<>();
        params.put("v", "dQw4w9WgXcQ");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        connection.setDoOutput(true);
        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
            writer.write(postDataBytes);
            writer.flush();
            writer.close();

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            System.out.println(content.toString());
        } finally {
            connection.disconnect();
        }
    }
}