package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;
import io.github.youssefrashidy.Benchmark.Adaptor.BenchMarkArray;
import io.github.youssefrashidy.Benchmark.Adaptor.BenchMarkArrayGenerator;
import io.github.youssefrashidy.Benchmark.Stat.BenchMark;
import io.github.youssefrashidy.Benchmark.Stat.QuickSortStat;
import io.github.youssefrashidy.Benchmark.Stat.SortBench;
import io.github.youssefrashidy.Benchmark.Stat.Stat;
import io.github.youssefrashidy.Benchmark.summary.SortComparison;
import io.github.youssefrashidy.Trees.AbstractBST;
import io.github.youssefrashidy.Trees.BST;
import io.github.youssefrashidy.Trees.RBBST;
import io.github.youssefrashidy.quicksort.QuickSort;
import io.github.youssefrashidy.quicksort.QuickSortBoxed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortBenchMarker {
    Benchmarker benchmarker = new Benchmarker();
    BenchMarkArrayGenerator generator = new BenchMarkArrayGenerator();

    public List<SortBench> runSortBenchmark() {
        RBBST<Integer, Integer> redBlack = new RBBST<>();
        BST<Integer, Integer> bst = new BST<>();
        QuickSort unBoxedSort = new QuickSort();
        QuickSortBoxed<Integer> boxedSort = new QuickSortBoxed<>();
        List<BenchMarkArray> arrays = new ArrayList<>();
        List<SortBench> bench = new ArrayList<>() ;
        for (ArrayType type : ArrayType.values()) {
            for (int i = 0; i < Benchmarker.NUM_ARRAYS; i++) {
                switch (type) {
                    case RANDOM -> arrays.add(generator.getRandomArray(100000));
                    case NEARLY_SORTED_10 -> arrays.add(generator.getNearlySorted_10(100000));
                    case NEARLY_SORTED_5 -> arrays.add(generator.getNearlySorted_5(100000));
                    case NEARLY_SORTED_1 -> arrays.add(generator.getNearlySorted_1(100000));
                }
            }
        }
        for (var array : arrays) {
            BenchMark runBST = new BenchMark(new ArrayList<>(), Operation.TreeSort);

            for (int i = 0; i < Benchmarker.WARMUP_ITERATIONS; i++) {
                benchmarker.runSingleBench(array, bst, Operation.TreeSort);
                bst.clear();
            }
            for (int i = 0; i < Benchmarker.MEASUREMENT_ITERATIONS; i++) {
                runBST.add(benchmarker.runSingleBench(array, bst, Operation.TreeSort));
                bst.clear();
            }

            BenchMark run = new BenchMark(new ArrayList<>(), Operation.TreeSort);

            for (int i = 0; i < Benchmarker.WARMUP_ITERATIONS; i++) {
                benchmarker.runSingleBench(array, redBlack, Operation.TreeSort);
                redBlack.clear();
            }
            for (int i = 0; i < Benchmarker.MEASUREMENT_ITERATIONS; i++) {
                run.add(benchmarker.runSingleBench(array, redBlack, Operation.TreeSort));
                redBlack.clear();
            }

            BenchMark runUnboxedQuick = new BenchMark(new ArrayList<>() , Operation.QuickSort) ;
            for (int i = 0; i < Benchmarker.WARMUP_ITERATIONS; i++) {
                var clone = new BenchMarkArray(array.inputDistribution(), array.array().clone()) ;
                runSingleQuickSort(clone,unBoxedSort) ;
            }
            for (int i = 0; i < Benchmarker.MEASUREMENT_ITERATIONS; i++) {
                var clone = new BenchMarkArray(array.inputDistribution(), array.array().clone()) ;
                runUnboxedQuick.add(runSingleQuickSort(clone,unBoxedSort)) ;
            }

            BenchMark runBoxedQuick = new BenchMark(new ArrayList<>() , Operation.QuickSort) ;
            for (int i = 0; i < Benchmarker.WARMUP_ITERATIONS; i++) {
                var clone = new BenchMarkArray(array.inputDistribution(), array.array().clone()) ;
                runSingleQuickSort(clone,boxedSort) ;
            }
            for (int i = 0; i < Benchmarker.MEASUREMENT_ITERATIONS; i++) {
                var clone = new BenchMarkArray(array.inputDistribution(), array.array().clone()) ;
                runBoxedQuick.add(runSingleQuickSort(clone,boxedSort)) ;
            }
            SortBench sortComparison = new SortBench(runBST,run,runUnboxedQuick,runBoxedQuick) ;
            bench.add(sortComparison) ;
        }
        return bench ;
    }

    private Stat runSingleQuickSort(BenchMarkArray array , QuickSort sort) {
        long start = System.nanoTime() ;
        sort.sort(array.array()) ;
        long end = System.nanoTime() ;
        return new QuickSortStat(sort.getClass().getSimpleName(), array.inputDistribution(), 0, end-start) ;
    }

    private Stat runSingleQuickSort(BenchMarkArray array , QuickSortBoxed<Integer> sort) {
        var boxedArray =  Arrays.stream(array.array()).boxed().toArray(Integer[]::new) ;
        long start = System.nanoTime() ;
        sort.sort(boxedArray) ;
        long end = System.nanoTime() ;
        return new QuickSortStat(sort.getClass().getSimpleName(), array.inputDistribution(), 0, end-start) ;
    }
}
