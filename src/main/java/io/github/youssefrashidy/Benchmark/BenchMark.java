package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.Benchmark.Stat.Stat;

import java.util.ArrayList;

public record BenchMark (ArrayList<Stat> stats) {
    public void add(Stat res) {
        stats.add(res) ;
    }
}
