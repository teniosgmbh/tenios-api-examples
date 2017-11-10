package teniosgmbh.retrievecdrs;

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

public class RetrieveCdrsTestClient {

    private static final String BASE_URL = "https://api.tevox.com"; // API base url

    private static final String CDRS_RETRIEVE = "cdrs/retrieve";

    private static final String ACCESS_KEY = "xxx3f096-xxxx-xxxx-xxxx-8353b27e2xxx"; // API access key of account. Can be found in 'General Settings page'
    private static final Integer PAGE = 1;
    private static final Integer PAGE_SIZE = 10;

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        RetrieveCdrsTestClient apiClient = new RetrieveCdrsTestClient();

        Response cdrResponse = apiClient.getCdrs();
        processResponse(cdrResponse);
    }

    private Response getCdrs() {
        Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        // Create request parameters
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("start_date_from", "2016-01-01T09:00:00.000Z"); // Retrieve cdrs for calls started after 'start_date_from'
        parameters.put("start_date_to", "2018-01-01T09:00:00.000Z"); // Retrieve cdrs for calls started before 'start_date_to'
        parameters.put("page", PAGE.toString()); // Page to retrieve, e.g. 1
        parameters.put("page_size", PAGE_SIZE.toString()); // Number of cdrs per page, e.g. 10

        String startRequest = String.format("Making request to Tevox api to retrieve cdrs. Request details: \n" +
                "Access key: %s\n" +
                "Page (integer value starting from 1): %d\n" +
                "Page size (integer value starting from 1): %d", ACCESS_KEY, PAGE, PAGE_SIZE);
        System.out.println(startRequest);

        return client.target(BASE_URL).path(CDRS_RETRIEVE)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
    }

    // Read JSON string from response and process results
    private static void processResponse(Response response) throws IOException {
        String jsonString = response.readEntity(String.class);
        JsonNode result = extractResultFromResponse(jsonString);

        processResult(result);
    }

    private static JsonNode extractResultFromResponse(String jsonString) throws IOException {
        return mapper.readTree(jsonString);
    }

    // Extract pagination info and process cdr items
    private static void processResult(JsonNode result) {
        Integer cdrsAmount = result.get("total_items").asInt();
        String resultMessage = String.format("\nReceived response from API\n" +
                "For selected range there are : %d cdrs", cdrsAmount);
        System.out.println(resultMessage);
        JsonNode cdrs = extractItemsFromResult(result);
        processCdrs(cdrs);
    }

    private static JsonNode extractItemsFromResult(JsonNode result) {
        return result.get("items");
    }

    // Loop over cdrs and print result
    private static void processCdrs(JsonNode result) {
        System.out.println("\nThe response contains the following cdrs:");
        for (final JsonNode cdr : result) {
            System.out.println(String.format("----------------------------------\n" +
                    "UUID : %s\n" +
                    "Call Type : %s\n" +
                    "Duration : %s\n" +
                    "Cost : %6.3f\n",
                    cdr.get("uuid").asText(),
                    cdr.get("call_type").asText(),
                    cdr.get("duration").asText(),
                    cdr.get("cost").asDouble()));
        }
    }
}
