package teniosgmbh.externalcallcontrol;

import javax.ws.rs.ext.ContextResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MyObjectMapperProvider implements ContextResolver<ObjectMapper> {

    private ObjectMapper objectMapper;

    public MyObjectMapperProvider()  {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        /* We need to omit NULL values, because we use one single Block class that contains all
         the attribues for all types of blocks. */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return objectMapper;
    }
}
