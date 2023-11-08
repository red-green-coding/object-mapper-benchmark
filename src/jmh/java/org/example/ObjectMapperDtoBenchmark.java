package org.example;

import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ObjectMapperDtoBenchmark {

    private Serializer serializer = new Serializer();
    private String json =
            """
			{"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2"]}}""";

    private Dto dto =
            new Dto("some", Dto.DtoEnum.B, new Dto.InnerDto(123l, List.of("1", "2", "3")));

    @Benchmark
    public Dto fromStringNewInstance() throws Exception {
        return serializer.newInstanceFromString(json);
    }

    @Benchmark
    public Dto fromStringStaticInstance() throws Exception {
        return serializer.staticInstanceFromString(json);
    }

    @Benchmark
    public String toStringNewInstance() throws Exception {
        return serializer.newInstanceToString(dto);
    }

    @Benchmark
    public String toStringStaticInstance() throws Exception {
        return serializer.staticInstanceToString(dto);
    }
}
