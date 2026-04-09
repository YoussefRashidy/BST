package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;

import java.util.Stack;

public record QuickSortStat(String dataStructure, ArrayType inputDistribution , int height , long executionTime) implements Stat {
    @Override
    public String dataStructure() {
        return dataStructure;
    }

    @Override
    public ArrayType inputDistribution() {
        return inputDistribution;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public long executionTime() {
        return executionTime;
    }
}
