package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;

public record Insertion(String dataStructure, ArrayType inputDistribution , int height , long executionTime) implements Stat {
}
