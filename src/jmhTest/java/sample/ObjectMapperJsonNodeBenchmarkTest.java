package sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.ObjectMapperDtoBenchmark.getNewObjectMapper;

import org.example.ObjectMapperDtoBenchmark;
import org.example.ObjectMapperJsonNodeBenchmark;
import org.junit.jupiter.api.Test;

public class ObjectMapperJsonNodeBenchmarkTest {

    ObjectMapperJsonNodeBenchmark sut = new ObjectMapperJsonNodeBenchmark();

    @Test
    public void newInstanceFromStringToJsonNode() throws Exception {
        var jsonNode = sut.newInstanceToJsonNode();

        var jsonString = getNewObjectMapper().writeValueAsString(jsonNode);
        assertThat(jsonString).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }

    @Test
    public void staticInstanceFromStringToJsonNode() throws Exception {
        var jsonNode = sut.staticInstanceToJsonNode();

        var jsonString = getNewObjectMapper().writeValueAsString(jsonNode);
        assertThat(jsonString).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }
}
