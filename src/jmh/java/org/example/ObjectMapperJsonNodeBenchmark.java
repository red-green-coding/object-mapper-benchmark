package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class ObjectMapperJsonNodeBenchmark {

    private Serializer serializer = new Serializer();
    private String json =
            """
			{"some":"some","dtoEnum":"B","innerDto":{"num":123,"strings":["1","2"]}}""";

    @Benchmark
    public JsonNode usingNewInstanceToJsonNode() throws Exception {
        return serializer.newInstanceFromStringToJsonNode(json);
    }

    @Benchmark
    public JsonNode usingStaticInstanceToJsonNode() throws Exception {
        return serializer.staticInstanceFromStringToJsonNode(json);
    }
}
