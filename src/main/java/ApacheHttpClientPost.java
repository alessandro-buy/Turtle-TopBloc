
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ApacheHttpClientPost {

    public static void main(String[] args) throws IOException {

        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            var request = new HttpPost("http://54.90.99.192:5000/challenge");
            //var request = new HttpPost("https://httpbin.org/post");
            request.setHeader("User-Agent", "Java client");
            request.setEntity(new StringEntity("My test data"));

            HttpResponse response = client.execute(request);

            var bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            var builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null) {

                builder.append(line);
                builder.append(System.lineSeparator());
            }

            System.out.println(builder);
        }
    }
}