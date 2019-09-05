package teniosgmbh.number_order;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class NumberOrderTestClient {

    private static final String BASE_URL = "https://api.tenios.test"; // API base url

    private static final String NUMBER_ORDER_URL = "number/order";

    private static final String ACCESS_KEY = "398xx216-xxxx-xxxx-b554-0bee5xxxcc29"; // Replace with access_key of account. Can be found in 'General Settings page'

    private static final String VERIFICATION_UUID = "3d47943f-e899-45db-a361-ee006da83665"; // Should be replaced with real verification document id

    private static final Client httpClient = ClientBuilder.newClient().register(JacksonJsonProvider.class);

    public static void main(String[] args) {
        NumberOrderTestClient numberOrderTestClient = new NumberOrderTestClient();

        numberOrderTestClient.orderNumber();
    }

    private void orderNumber() {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("verification_id", VERIFICATION_UUID);
        parameters.put("number_type", "GEOGRAPHICAL");

        System.out.println("Sending request to order number.");

        Response response = httpClient.target(BASE_URL).path(NUMBER_ORDER_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
        int responseStatus = response.getStatus();
        if (responseStatus != 200) {
            System.err.println("Incorrect status code " + responseStatus);
        } else {
            HashMap<String, Object> map = response.readEntity(new GenericType<HashMap<String, Object>>() {
            });
            Integer orderId = (Integer) map.get("order_id");
            System.out.println("Number order created. Id:  " + orderId);
        }
    }
}
