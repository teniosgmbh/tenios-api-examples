package teniosgmbh.verification_document;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;

public class VerificationDocTestClient {

    private static final String BASE_URL = "https://api.tenios.test"; // API base url

    private static final String VERIFICATION_CREATE_URL = "verification/create";

    private static final String ACCESS_KEY = "398xx216-xxxx-xxxx-b554-0bee5xxxcc29"; // Replace with access_key of account. Can be found in 'General Settings page'

    private static final Client httpClient = ClientBuilder.newClient().register(JacksonJsonProvider.class);

    public static void main(String[] args) throws Exception {
        VerificationDocTestClient verificationDocTestClient = new VerificationDocTestClient();

        verificationDocTestClient.uploadDocument();

    }

    private void uploadDocument() throws Exception {
        try (
                InputStream is = VerificationDocTestClient.class.getResourceAsStream("/verification_document.pdf");
                ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            byte[] bytes = buffer.toByteArray();

            byte[] encodedString = Base64.getEncoder().encode(bytes);

            String testRequest = "{\n" +
                    "  \"access_key\":\"" + ACCESS_KEY + "\",\n" +
                    "  \"country\":\"Germany\",\n" +
                    "  \"city\":\"Aarbergen\",\n" +
                    "  \"zip\":\"50670\",\n" +
                    "  \"street\":\"Musterstr.\",\n" +
                    "  \"house_number\":\"13\",\n" +
                    "  \"document_type\":\"CONTRACT\",\n" +
                    "  \"area_code\":\"221\",\n" +
                    "  \"document_data\":\"" + new String(encodedString) + "\"\n" +
                    "}";

            System.out.println("Trying to upload verification document.");

            Response response = httpClient.target(BASE_URL).path(VERIFICATION_CREATE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(testRequest, MediaType.APPLICATION_JSON));

            int responseStatus = response.getStatus();
            if (responseStatus != 200) {
                System.err.println("Incorrect status code " + responseStatus);
            } else {
                HashMap<String, Object> map = response.readEntity(new GenericType<HashMap<String, Object>>() {
                });
                String verificationId = (String) map.get("verification_id");
                System.out.println("Verification document successfully uploaded. Document uuid: " + verificationId);
            }
        }
    }
}
