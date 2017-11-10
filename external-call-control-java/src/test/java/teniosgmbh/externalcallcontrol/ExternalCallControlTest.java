package teniosgmbh.externalcallcontrol;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.ByteStreams;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ExternalCallControlTest {

    @Test
    public void testExternalCallControl() throws IOException, NoSuchAlgorithmException {

        final SSLContext sslContext = provideSSLContext();

        final Client jerseyClient = provideJerseyClient(sslContext);

        /* Loop count 0 */
        {
            Entity entity = Entity.entity("{"
                    + " \"requestType\":\"EXTERNAL_CALL_CONTROL\","
                    + " \"customerNumber\":200000,"
                    + " \"accessKey\":\"1234\","
                    + " \"variables\":"
                    + "  {\"destination_number\":\"+4912345678\","
                    + "   \"answered_time\":\"0\","
                    + "   \"caller_id_number\":\"+4989444444444\"},"
                    + " \"loopCount\":0,"
                    + " \"requestStatus\":\"REQUESTING_BLOCKS\","
                    + " \"blocksProcessingResult\":null"
                    + "}", MediaType.APPLICATION_JSON);

            Response response = jerseyClient.target("https://localhost:8333/test/externalpost")
                    .request("application/json").post(entity);

            Assert.assertEquals(200, response.getStatus());
            final JsonNode responseNode = response.readEntity(JsonNode.class);

            Assert.assertEquals("ANNOUNCEMENT", responseNode.get("blocks").get(0).get("blockType").textValue());
            Assert.assertEquals("Voicemail_Ansage", responseNode.get("blocks").get(0).get("announcementName").textValue());

        }

        /* Loop count 1 */
        {
            Entity entity = Entity.entity("{"
                    + " \"requestType\":\"EXTERNAL_CALL_CONTROL\","
                    + " \"customerNumber\":200000,"
                    + " \"accessKey\":\"1234\","
                    + " \"variables\":"
                    + "  {\"destination_number\":\"+4912345678\","
                    + "   \"answered_time\":\"0\","
                    + "   \"caller_id_number\":\"+4989444444444\"},"
                    + " \"loopCount\":1,"
                    + " \"requestStatus\":\"REQUESTING_BLOCKS\","
                    + " \"blocksProcessingResult\":null"
                    + "}", MediaType.APPLICATION_JSON);

            Response response = jerseyClient.target("https://localhost:8333/test/externalpost")
                    .request("application/json").post(entity);

            Assert.assertEquals(200, response.getStatus());
            final JsonNode responseNode = response.readEntity(JsonNode.class);

            Assert.assertEquals("BRIDGE", responseNode.get("blocks").get(0).get("blockType").textValue());
            Assert.assertEquals("1004", responseNode.get("blocks").get(0).get("destinations").get(1).get("destination").textValue());

        }

        /* Loop count 2 */
        {
            Entity entity = Entity.entity("{"
                    + " \"requestType\":\"EXTERNAL_CALL_CONTROL\","
                    + " \"customerNumber\":200000,"
                    + " \"accessKey\":\"1234\","
                    + " \"variables\":"
                    + "  {\"destination_number\":\"+4912345678\","
                    + "   \"answered_time\":\"0\","
                    + "   \"caller_id_number\":\"+4989444444444\"},"
                    + " \"loopCount\":2,"
                    + " \"requestStatus\":\"REQUESTING_BLOCKS\","
                    + " \"blocksProcessingResult\":null"
                    + "}", MediaType.APPLICATION_JSON);

            Response response = jerseyClient.target("https://localhost:8333/test/externalpost")
                    .request("application/json").post(entity);

            Assert.assertEquals(200, response.getStatus());
            final JsonNode responseNode = response.readEntity(JsonNode.class);

            Assert.assertTrue(responseNode.get("blocks").path(0).isMissingNode()); // no more blocks
        }

    }

    Client provideJerseyClient(SSLContext sslContext) {
        final ClientBuilder builder = ClientBuilder.newBuilder()
                .register(JacksonFeature.class)
                .property(ClientProperties.READ_TIMEOUT, 4_000);

        if (sslContext != null)
            builder.sslContext(sslContext);

        return builder.build();
    }

    SSLContext provideSSLContext() throws NoSuchAlgorithmException, IOException {

        return SslConfigurator.newInstance()
                .trustStoreBytes(ByteStreams.toByteArray(getClass().getResourceAsStream("/truststore_client_example")))
                .trustStorePassword("123456")
                .createSSLContext();
    }
}
