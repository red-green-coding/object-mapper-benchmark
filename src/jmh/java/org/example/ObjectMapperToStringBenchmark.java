package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ObjectMapperToStringBenchmark {

    private Serializer serializer = new Serializer();
    private Dto dto =
            new Dto("some", Dto.DtoEnum.B, new Dto.InnerDto(123l, List.of("1", "2", "3")));

    @Benchmark
    public void usingNewInstance(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.newInstanceToString(dto));
    }

    @Benchmark
    public void usingStaticInstance(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.staticInstanceToString(dto));
    }
}
