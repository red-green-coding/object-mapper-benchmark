package org.example;

import static org.example.ObjectMapperDtoBenchmark.getNewObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ObjectMapperInstantiationBenchmark {

    @Benchmark
    public ObjectMapper createNewObjectMapper() {
        return getNewObjectMapper();
    }
}
