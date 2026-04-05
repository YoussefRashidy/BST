package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;

public record Deletion(String dataStructure, ArrayType inputDistribution , int height , long executionTime) implements Stat {
}
