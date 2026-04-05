package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;

public sealed interface Stat permits Insertion , Deletion , Contains , TreeSort {
    String dataStructure();
    ArrayType inputDistribution();
    int height();
    long executionTime();
}
