package teniosgmbh.httpsposts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

@Path("httpspost")
public class MyHttpsPost {

    private static Logger LOG = Logger.getLogger(MyHttpsPost.class.getName());

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void externalPost(final JsonNode rootNode) throws IOException {

        /* A request may look something like
           {
             "requestType":"CALL_START",
             "customerNumber":200000,
             "accessKey":"1234",
             "variables":{"destination_number":"+4912345678","answered_time":"0","caller_id_number":"+4989444444444"}
            }
         */

        LOG.info("-------------------- httpsPost ---------------------");

        /* First check the access key. Please replace "1234" with the access key you specified
           in your API Routing Control block (in the tenios customer portal) */
        final String accessKey = rootNode.get("accessKey").textValue();
        if (!"1234".equals(accessKey)) {
            LOG.severe("Illegal access key");
            throw new RuntimeException();
        }

        final String requestType = rootNode.get("requestType").textValue();
        LOG.info("Request type: " + requestType);

        final int customerNumber = rootNode.get("customerNumber").intValue();
        LOG.info("Customer number: " + customerNumber);


        /* Let's have a look at the variables node in the request */

        final ObjectNode variablesNode = (ObjectNode) rootNode.get("variables");

        /* We can get a specific variable value: */

        final JsonNode destinationNumberNode = variablesNode.get("destination_number");
        if (destinationNumberNode != null) {
            LOG.info("Destination number is: " + destinationNumberNode.textValue());
        } else {
            LOG.info("The request contained no destination number");
        }

        /* And here is how to iterate over all variables: */

        LOG.info("------------ Iteration over all variables: ------------");
        final Iterator<String> fieldNames = variablesNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            final String value = variablesNode.get(fieldName).asText(); // asText converts any type into a String
            LOG.info("Variable " + fieldName + " = " + value);
        }
    }
}
