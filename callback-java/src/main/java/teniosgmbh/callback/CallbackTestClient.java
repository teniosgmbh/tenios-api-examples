package teniosgmbh.callback;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class CallbackTestClient {
    private static final String BASE_URL = "https://api.tevox.com"; // API base url

    private static final String CALLBACK_INIT = "callback/init";

    private static final String CALLBACK_STATUS = "callback/status";

    private static final String ACCESS_KEY = "398xx216-xxxx-xxxx-b554-0bee5xxxcc29"; // Replace with access_key of account. Can be found in 'General Settings page'

    private static final String CALLBACK_CONFIG_ID = "xxx3f096-xxxx-xxxx-xxxx-8353b27e2xxx"; // Can be found in 'callback configuration' section of 'Portal'

    private static final String CALLBACK_NUMBER = "+49123456789"; // Phone number to call back to.

    private static final String DELAY = "0"; // Delay in seconds

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        CallbackTestClient callbackTestClient = new CallbackTestClient();
        Integer callbackId = callbackTestClient.initCallbackRequest();
        String status = callbackTestClient.statusCallbackRequest(callbackId);
        for (int i = 0; i < 3 && status.equals("REQUESTED"); i++) {
            TimeUnit.SECONDS.sleep(3);
            status = callbackTestClient.statusCallbackRequest(callbackId);
        }
    }

    // A method to initialize callback
    private Integer initCallbackRequest() throws IOException {
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        // Init callback request parameters
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("callback_config_id", CALLBACK_CONFIG_ID);
        parameters.put("callback_number", CALLBACK_NUMBER); // Phone number to call back to.
        parameters.put("delay", DELAY); // Delay in seconds

        String initMessage = String.format("Initializing callback with parameters: \n" +
                "Access key: %s\n" +
                "Phone number: %s\n" +
                "Delay: %s\n", ACCESS_KEY, CALLBACK_NUMBER, DELAY);
        System.out.println(initMessage);

        Response response = client.target(BASE_URL).path(CALLBACK_INIT)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));

        return extractCallbackId(response);
    }

    // Extract id of registered callback from response
    private Integer extractCallbackId(Response response) throws IOException {
        String jsonString = response.readEntity(String.class);
        JsonNode result = mapper.readTree(jsonString);

        return result.get("id").asInt();
    }

    // A method to request registered callback status
    private String statusCallbackRequest(Integer callbackId) throws IOException {
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        // Status callback request parameters
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("id", callbackId.toString());

        Response response = client.target(BASE_URL).path(CALLBACK_STATUS)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));

        String status = extractStatus(response);

        String statusMessage = String.format("------------------------------------------------\n" +
                "Checking callback status for callback id: %d\n" +
                "Callback status: %s", callbackId, status);
        System.out.println(statusMessage);
        return status;
    }

    // Extract callback status from response
    private String extractStatus(Response response) throws IOException {
        String jsonString = response.readEntity(String.class);
        JsonNode result = mapper.readTree(jsonString);

        return result.get("status").asText();
    }
}
