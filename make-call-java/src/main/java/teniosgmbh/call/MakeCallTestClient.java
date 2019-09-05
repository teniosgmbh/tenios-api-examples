package teniosgmbh.call;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MakeCallTestClient {
    private static final String BASE_URL = "https://api.tenios.com"; // API base url

    private static final String MAKE_CALL = "makecall/init";

    private static final String MAKE_CALL_STATUS = "makecall/status";

    private static final String ACCESS_KEY = "XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXXX"; // Replace with access_key of account. Can be found in 'General Settings page'

    private static final String DESTINATION_NUMBER = "+49123456789"; // The number which will be called.

    private static final String TENIOS_NUMBER = "+491234567899"; // One of your numbers at tenios.

    public static void main(String[] args) throws InterruptedException {
        MakeCallTestClient makeCallTestClient = new MakeCallTestClient();
        Integer callId = makeCallTestClient.initCallRequest();
        System.out.println("Call id: "+ callId);
        String status = makeCallTestClient.statusCallRequest(callId);
        for (int i = 0; i < 3 && status.equals("REQUESTED"); i++) {
            TimeUnit.SECONDS.sleep(3);
            status = makeCallTestClient.statusCallRequest(callId);
        }
    }

    // A method to initialize make call request
    private Integer initCallRequest() {
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("destination_number", DESTINATION_NUMBER);
        parameters.put("tenios_number", TENIOS_NUMBER); // Phone number to call back to.

        String initMessage = String.format("Initializing make call request with parameters: \n" +
                "Access key: %s\n" +
                "Destination number: %s\n" +
                "Tenios number: %s\n", ACCESS_KEY, DESTINATION_NUMBER, TENIOS_NUMBER);
        System.out.println(initMessage);

        Response response = client.target(BASE_URL).path(MAKE_CALL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));

        return extractCallId(response);
    }

    // Extract id of registered call from response
    private Integer extractCallId(Response response) {
        JsonNode result = response.readEntity(JsonNode.class);
        System.out.println(result);

        return result.get("id").asInt();
    }

        // A method to request registered call status
    private String statusCallRequest(Integer callId) {
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        // Status callback request parameters
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("id", callId.toString());

        Response response = client.target(BASE_URL).path(MAKE_CALL_STATUS)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));

        String status = extractStatus(response);

        String statusMessage = String.format("------------------------------------------------\n" +
                "Checking call status for call id: %d\n" +
                "Call status: %s", callId, status);
        System.out.println(statusMessage);
        return status;
    }

    // Extract callback status from response
    private String extractStatus(Response response) {
        JsonNode result = response.readEntity(JsonNode.class);

        return result.get("status").asText();
    }
}
