package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ObjectMapperInstantiationBenchmark {

    @Benchmark
    public void createNewObjectMapper(Blackhole bh) throws JsonProcessingException {
        bh.consume(Serializer.getNewObjectMapper());
    }

}
