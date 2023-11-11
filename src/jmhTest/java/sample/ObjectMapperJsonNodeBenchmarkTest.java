package sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.ObjectMapperDtoBenchmark;
import org.example.ObjectMapperJsonNodeBenchmark;
import org.example.Serializer;
import org.junit.jupiter.api.Test;

public class ObjectMapperJsonNodeBenchmarkTest {

    ObjectMapperJsonNodeBenchmark sut = new ObjectMapperJsonNodeBenchmark();

    @Test
    public void newInstanceFromStringToJsonNode() throws Exception {
        var jsonNode = sut.newInstanceToJsonNode();

        var jsonString = Serializer.getNewObjectMapper().writeValueAsString(jsonNode);
        assertThat(jsonString).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }

    @Test
    public void staticInstanceFromStringToJsonNode() throws Exception {
        var jsonNode = sut.staticInstanceToJsonNode();

        var jsonString = Serializer.getNewObjectMapper().writeValueAsString(jsonNode);
        assertThat(jsonString).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }
}
