package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;

public record BenchPair(BenchMark RB , BenchMark BST , Operation op , ArrayType type) {
}
