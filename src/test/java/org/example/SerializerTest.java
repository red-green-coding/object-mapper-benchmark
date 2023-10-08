package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SerializerTest {
    Dto DTO_INSTANCE = new Dto("some", Dto.DtoEnum.B, new Dto.InnerDto(123L, List.of("1", "2")));

    String JSON =
            """
			{"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2"]}}""";

    @Test
    public void staticInstanceToString() throws JsonProcessingException {
        var serializer = new Serializer();

        String json = serializer.staticInstanceToString(DTO_INSTANCE);
        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void staticInstanceFromString() throws JsonProcessingException {
        var serializer = new Serializer();

        assertThat(serializer.staticInstanceFromString(JSON))
                .usingRecursiveComparison()
                .isEqualTo(DTO_INSTANCE);
    }

    @Test
    public void newInstanceToString() throws JsonProcessingException {
        var serializer = new Serializer();

        String json = serializer.newInstanceToString(DTO_INSTANCE);
        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void newInstanceFromString() throws JsonProcessingException {
        var serializer = new Serializer();

        assertThat(serializer.newInstanceFromString(JSON))
                .usingRecursiveComparison()
                .isEqualTo(DTO_INSTANCE);
    }

    @Test
    public void newInstanceFromStringToJsonNode() throws JsonProcessingException {
        var serializer = new Serializer();

        JsonNode jsonNode = serializer.newInstanceFromStringToJsonNode(JSON);

        assertThat(serializer.staticInstanceToString(jsonNode)).isEqualTo(JSON);
    }

    @Test
    public void staticInstanceFromStringToJsonNode() throws JsonProcessingException {
        var serializer = new Serializer();

        JsonNode jsonNode = serializer.staticInstanceFromStringToJsonNode(JSON);

        assertThat(serializer.staticInstanceToString(jsonNode)).isEqualTo(JSON);
    }
}
