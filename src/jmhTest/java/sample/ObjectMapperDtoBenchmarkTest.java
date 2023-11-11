package sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.example.Dto;
import org.example.ObjectMapperDtoBenchmark;
import org.junit.jupiter.api.Test;

public class ObjectMapperDtoBenchmarkTest {

    Dto DTO_INSTANCE = new Dto("some", Dto.DtoEnum.B, new Dto.InnerDto(123L, List.of("1", "2")));

    String JSON =
            """
			{"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2","3"]}}""";

    ObjectMapperDtoBenchmark sut = new ObjectMapperDtoBenchmark();

    @Test
    public void staticInstanceToString() throws Exception {
        String json = sut.toStringStaticInstance();
        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void staticInstanceFromString() throws Exception {
        assertThat(sut.fromStringStaticInstance())
                .usingRecursiveComparison()
                .isEqualTo(DTO_INSTANCE);
    }

    @Test
    public void newInstanceToString() throws Exception {
        String json = sut.toStringNewInstance();
        assertThat(json).isEqualTo(JSON);
    }

    @Test
    public void newInstanceFromString() throws Exception {
        assertThat(sut.fromStringNewInstance()).usingRecursiveComparison().isEqualTo(DTO_INSTANCE);
    }
}
