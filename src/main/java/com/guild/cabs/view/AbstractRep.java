package com.guild.cabs.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guild.cabs.view.json.CabsObjectMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonPropertyOrder(alphabetic = true)
public abstract class AbstractRep {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractRep.class);

    public String toJson() throws JsonProcessingException {
        final ObjectMapper mapper = CabsObjectMapperFactory.getObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public String toJsonRisky() {
        try {
            return toJson();
        } catch(JsonProcessingException e) {
            LOG.error(
                "Error serializing JSON for " + getClass().getName(),
                e
            );
            return e.getMessage();
        }
    }

    @Override
    public String toString() {
        try {
            return this.toJson();
        } catch(JsonProcessingException e) {
            LOG.error(
                "Error serializing rep",
                e
            );
            return super.toString();
        }
    }
}
