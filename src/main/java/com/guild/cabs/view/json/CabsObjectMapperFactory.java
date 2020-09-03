package com.guild.cabs.view.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CabsObjectMapperFactory {

    public static ObjectMapper getObjectMapper() {

        final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // ----------------------------------------------------------------
        // CUSTOM SERIALIZERS & DESERIALIZERS CAN BE REGISTERED HERE
        // ----------------------------------------------------------------
        return mapper;
    }
}
