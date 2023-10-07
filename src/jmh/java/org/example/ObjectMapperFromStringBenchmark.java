package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class ObjectMapperFromStringBenchmark {

    private Serializer serializer = new Serializer();
    private String json = """
            {"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2"]}}""";


    @Benchmark
    public void newInstanceFromString(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.newInstanceFromString(json));
    }

    @Benchmark
    public void staticInstanceFromString(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.staticInstanceFromString(json));
    }

    @Benchmark
    public void newInstanceFromStringToJsonNode(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.newInstanceFromStringToJsonNode(json));
    }

    @Benchmark
    public void staticInstanceFromStringToJsonNode(Blackhole bh) throws JsonProcessingException {
        bh.consume(serializer.staticInstanceFromStringToJsonNode(json));
    }
}
