package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;
import io.github.youssefrashidy.Benchmark.Operation;

public record BenchPair(BenchMark RB , BenchMark BST , Operation op , ArrayType type) {
}
