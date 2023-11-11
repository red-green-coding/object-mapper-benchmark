package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ObjectMapperDtoBenchmark {

    public static final String JSON =
            """
			{"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2","3"]}}""";

    public static final Dto DTO =
            new Dto("some", Dto.DtoEnum.B, new Dto.InnerDto(123L, List.of("1", "2", "3")));

    public static ObjectMapper getNewObjectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule());
    }

    public static final ObjectMapper MAPPER = getNewObjectMapper();

    @Benchmark
    public Dto fromStringNewInstance() throws Exception {
        return getNewObjectMapper().readValue(JSON, Dto.class);
    }

    @Benchmark
    public Dto fromStringStaticInstance() throws Exception {
        return MAPPER.readValue(JSON, Dto.class);
    }

    @Benchmark
    public String toStringNewInstance() throws Exception {
        return getNewObjectMapper().writeValueAsString(DTO);
    }

    @Benchmark
    public String toStringStaticInstance() throws Exception {
        return MAPPER.writeValueAsString(DTO);
    }
}
