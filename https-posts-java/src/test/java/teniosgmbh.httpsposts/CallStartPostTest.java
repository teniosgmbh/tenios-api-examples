package teniosgmbh.httpsposts;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

public class CallStartPostTest {

    @Test
    public void testCallStart() throws NoSuchAlgorithmException, IOException {

        final SSLContext sslContext = provideSSLContext();

        final Client jerseyClient = provideJerseyClient(sslContext);

        Entity entity = Entity.entity("{"
                + " \"requestType\":\"CALL_START\","
                + " \"customerNumber\":200000,"
                + " \"accessKey\":\"1234\","
                + " \"variables\":"
                + "  {\"destination_number\":\"+4912345678\",\"answered_time\":\"0\",\"caller_id_number\":\"+4989444444444\"}"
                + "}", MediaType.APPLICATION_JSON);

        final Response response = jerseyClient.target("https://localhost:8334/test/httpspost")
                .request("application/json").post(entity);

        Assert.assertTrue(response.getStatus() == 200 || response.getStatus() == 204); // Both 200 and 204 are fine

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