package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class Serializer {

    public static ObjectMapper getNewObjectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule());
    }

    public String newInstanceToString(Dto dto) throws JsonProcessingException {
        return getNewObjectMapper().writeValueAsString(dto);
    }

    public Dto newInstanceFromString(String json) throws JsonProcessingException {
        return getNewObjectMapper().readValue(json, Dto.class);
    }

    public JsonNode newInstanceFromStringToJsonNode(String json) throws JsonProcessingException {
        return getNewObjectMapper().readTree(json);
    }

    private static final ObjectMapper mapper = getNewObjectMapper();

    public String staticInstanceToString(Object dto) throws JsonProcessingException {
        return mapper.writeValueAsString(dto);
    }

    public Dto staticInstanceFromString(String json) throws JsonProcessingException {
        return mapper.readValue(json, Dto.class);
    }

    public JsonNode staticInstanceFromStringToJsonNode(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }
}
