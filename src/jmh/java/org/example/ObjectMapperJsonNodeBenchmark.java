package org.example;

import static org.example.ObjectMapperDtoBenchmark.MAPPER;
import static org.example.ObjectMapperDtoBenchmark.getNewObjectMapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ObjectMapperJsonNodeBenchmark {

    @Benchmark
    public JsonNode newInstanceToJsonNode() throws Exception {
        return getNewObjectMapper().readTree(ObjectMapperDtoBenchmark.JSON);
    }

    @Benchmark
    public JsonNode staticInstanceToJsonNode() throws Exception {
        return MAPPER.readTree(ObjectMapperDtoBenchmark.JSON);
    }
}
