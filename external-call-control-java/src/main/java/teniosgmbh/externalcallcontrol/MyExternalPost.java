package teniosgmbh.externalcallcontrol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import teniosgmbh.externalcallcontrol.model.Block;
import teniosgmbh.externalcallcontrol.model.CallControlResponse;
import teniosgmbh.externalcallcontrol.model.Destination;
import teniosgmbh.externalcallcontrol.model.ExternalPostBlockParams;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.logging.Logger;

@Path("externalpost")
public class MyExternalPost {

    private static Logger LOG = Logger.getLogger(MyExternalPost.class.getName());

    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CallControlResponse externalPost(final ExternalPostBlockParams params) throws IOException {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);


        LOG.info("-------------------- externalPost ---------------------");

        /* First check the access key. Please replace "1234" with the access key you specified
           in your API Routing Control block (in the tenios customer portal) */
        if (!"1234".equals(params.getAccessKey())) {
            LOG.severe("Illegal access key");
            throw new RuntimeException();
        }

        /*
           Normally, you will get a REQUESTING_BLOCKS request, with loop count starting at 0.
           When you then send some blocks back, you will get another REQUESTING_BLOCKS request, with loop count 1.
           This goes on until either the call ends, or you send back an empty CallControlResponse.
         */
        switch (params.getRequestStatus()) {

            case REQUESTING_BLOCKS:

                LOG.info("--------- request (loop count " + params.getLoopCount() + ") ---------");

                final String callerIdNumber = params.getVariables().get("caller_id_number");

                /* You can react to certain variable values, e.g. you can reject a certain caller id */
                if ("+4912345678".equals(callerIdNumber)) {

                    return new CallControlResponse(
                            Block.newHangupBlock("CALL_REJECTED")
                    );

                } else {

                    switch (params.getLoopCount()) {
                        case 0:

                            return new CallControlResponse(
                                    Block.newAnnouncementBlock("Voicemail_Ansage", true),
                                    Block.newSayBlock("en-au.female.1", false, "Hello there")
                            );


                        case 1:

                            return new CallControlResponse(
                                    Block.newBridgeBlock("SEQUENTIAL",
                                            new Destination("SIP_USER", "1003", null),
                                            new Destination("SIP_USER", "1004", null)),
                                    Block.newAnnouncementBlock("My_Announcement", false),
                                    Block.newSayBlock("de.male.1", false, "Vielen Dank und auf Wiedersehen!")
                            );

                        default:

                            return new CallControlResponse();
                    }
                }

            case VALIDATION_ERRORS:

                LOG.warning("--------- validationErrors ---------");
                LOG.warning(params.getBlocksProcessingResult().getValidationErrors().toString());
                return null;

            default:
                throw new RuntimeException("Unexpected request status: " + params.getRequestStatus());
        }
        
    }
}
