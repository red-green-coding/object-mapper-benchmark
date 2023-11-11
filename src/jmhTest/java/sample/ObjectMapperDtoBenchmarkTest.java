package sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.example.ObjectMapperDtoBenchmark;
import org.junit.jupiter.api.Test;

public class ObjectMapperDtoBenchmarkTest {

    ObjectMapperDtoBenchmark sut = new ObjectMapperDtoBenchmark();

    @Test
    public void staticInstanceToString() throws Exception {
        String json = sut.toStringStaticInstance();
        assertThat(json).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }

    @Test
    public void staticInstanceFromString() throws Exception {
        assertThat(sut.fromStringStaticInstance())
                .usingRecursiveComparison()
                .isEqualTo(ObjectMapperDtoBenchmark.DTO);
    }

    @Test
    public void newInstanceToString() throws Exception {
        String json = sut.toStringNewInstance();
        assertThat(json).isEqualTo(ObjectMapperDtoBenchmark.JSON);
    }

    @Test
    public void newInstanceFromString() throws Exception {
        assertThat(sut.fromStringNewInstance())
                .usingRecursiveComparison()
                .isEqualTo(ObjectMapperDtoBenchmark.DTO);
    }
}
