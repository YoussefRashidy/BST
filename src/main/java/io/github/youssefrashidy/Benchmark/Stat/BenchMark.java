package io.github.youssefrashidy.Benchmark.Stat;

import io.github.youssefrashidy.Benchmark.Operation;

import java.util.ArrayList;

public record BenchMark (ArrayList<Stat> stats , Operation operation) {
    public void add(Stat res) {
        stats.add(res) ;
    }
}
