package teniosgmbh.verification_document;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class RecordingTestClient {

    private static final String BASE_URL = "https://api.tenios.test"; // API base url

    private static final String RECORDING_START_URL = "record-call/start";

    private static final String RECORDING_STOP_URL = "record-call/stop";

    private static final String ACCESS_KEY = "398xx216-xxxx-xxxx-b554-0bee5xxxcc29"; // Replace with access_key of account. Can be found in 'General Settings page'

    private static final String CALL_UUID = "4f5cde74-ef04-4894-b761-a6bb11d29878"; // Should be replaced with real call uuid

    private static final Client httpClient = ClientBuilder.newClient().register(JacksonJsonProvider.class);

    public static void main(String[] args) {
        RecordingTestClient recordingTestClient = new RecordingTestClient();

        String recordingUuid = recordingTestClient.startCallRecording(CALL_UUID);

        recordingTestClient.stopCallRecording(CALL_UUID, recordingUuid);
    }

    private String startCallRecording(String callUuid) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("call_uuid", callUuid);

        System.out.println("Starting recording for call: " + callUuid);

        Response response = httpClient.target(BASE_URL).path(RECORDING_START_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
        int responseStatus = response.getStatus();
        if (responseStatus != 200) {
            System.err.println("Incorrect status code " + responseStatus);
        } else {
            HashMap<String, Object> map = response.readEntity(new GenericType<HashMap<String, Object>>() {});
            String recordingUuid = (String) map.get("recording_uuid");
            System.out.println("Recording started for call " + callUuid + " Recording uuid: " + recordingUuid);
            return recordingUuid;
        }
        return null;
    }

    private void stopCallRecording(String callUuid, String recordingUuid) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("access_key", ACCESS_KEY);  // API access key of account. Can be found in 'General Settings page'
        parameters.put("call_uuid", callUuid);
        parameters.put("recording_uuid", recordingUuid);

        System.out.println("\nSending request to to stop recording for call: " + callUuid + " recording uuid: " + recordingUuid);

        Response response = httpClient.target(BASE_URL).path(RECORDING_STOP_URL)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(parameters, MediaType.APPLICATION_JSON));
        int responseStatus = response.getStatus();
        if (responseStatus != 200) {
            System.err.println("Incorrect status code " + responseStatus);
        } else {
            HashMap<String, Object> map = response.readEntity(new GenericType<HashMap<String, Object>>() {});
            Boolean success = (Boolean) map.get("success");
            System.out.println("Recording stopped request result: " + success);
        }
    }
}
