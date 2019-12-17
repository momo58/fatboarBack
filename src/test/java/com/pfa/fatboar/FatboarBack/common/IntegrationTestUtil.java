package com.pfa.fatboar.FatboarBack.common;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

public class IntegrationTestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static byte[] convertObjectToJsonBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
