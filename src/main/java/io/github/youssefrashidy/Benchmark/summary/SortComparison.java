package io.github.youssefrashidy.Benchmark.summary;

public record SortComparison(StatSummary BSTSummary, StatSummary RBTreeSummary , StatSummary quickSortSummary , StatSummary quickSortBoxedSummary) {
}
