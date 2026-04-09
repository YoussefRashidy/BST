package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.Benchmark.Stat.BenchMark;
import io.github.youssefrashidy.Benchmark.Stat.BenchPair;
import io.github.youssefrashidy.Benchmark.Stat.SortBench;
import io.github.youssefrashidy.Benchmark.Stat.Stat;
import io.github.youssefrashidy.Benchmark.summary.PairSummary;
import io.github.youssefrashidy.Benchmark.summary.SortComparison;
import io.github.youssefrashidy.Benchmark.summary.StatSummary;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class FeatureExtractor {
    public List<PairSummary> extractFeatures(List<BenchPair> pairs) {
        List<PairSummary> summaries = new ArrayList<>();
        for (var pair : pairs) {
            StatSummary bstSummary = getFullSummaryBST(pair.BST());
            StatSummary rbSummary = getFullSummaryRB(pair.RB(), bstSummary.mean());
            PairSummary pairSummary = new PairSummary(bstSummary, rbSummary);
            summaries.add(pairSummary);
        }
        return summaries;
    }

    public List<SortComparison> extractSortFeatures(List<SortBench> benches) {
        List<SortComparison> summaries = new ArrayList<>();
        for (var bench : benches) {
            StatSummary bstSummary = getFullSummaryBST(bench.BST());
            StatSummary rbSummary = getFullSummaryRB(bench.RB(), bstSummary.mean());
            StatSummary unboxedSummary = getFullSummaryRB(bench.QuickSortUnboxed(), bstSummary.mean());
            StatSummary boxedSummary = getFullSummaryRB(bench.QuickSortBoxed(),bstSummary.mean());
            SortComparison sortComparison = new SortComparison(bstSummary, rbSummary, unboxedSummary, boxedSummary);
            summaries.add(sortComparison);
        }
        return summaries;
    }

    // TODO : Use builder
    private StatSummary getFullSummaryRB(BenchMark benchMark, double base) {
        var meanSummary = getMeanSummary(benchMark);
        double mean = meanSummary.getAverage();
        double median = getMedian(benchMark);
        double std = getStandardDeviation(benchMark);
        return new StatSummary(meanSummary.getCount(), meanSummary.getMin(), meanSummary.getMax(), mean, median, std,
                benchMark.stats().getFirst().height(),
                benchMark.stats().getFirst().dataStructure(),
                benchMark.stats().getFirst().inputDistribution(),
                benchMark.operation(),
                base / mean);
    }

    private StatSummary getFullSummaryBST(BenchMark benchMark) {
        var meanSummary = getMeanSummary(benchMark);
        double mean = meanSummary.getAverage();
        double median = getMedian(benchMark);
        double std = getStandardDeviation(benchMark);
        return new StatSummary(meanSummary.getCount(), meanSummary.getMin(), meanSummary.getMax(), mean, median, std,
                benchMark.stats().getFirst().height(),
                benchMark.stats().getFirst().dataStructure(),
                benchMark.stats().getFirst().inputDistribution(),
                benchMark.operation(),
                1);
    }


    private double getMean(BenchMark benchMark) {
        return benchMark.stats().stream()
                .mapToDouble(Stat::executionTime)
                .average()
                .orElse(0.0);
    }

    private DoubleSummaryStatistics getMeanSummary(BenchMark benchMark) {
        return benchMark.stats().stream().mapToDouble(Stat::executionTime).summaryStatistics();
    }

    private double getMedian(BenchMark benchMark) {
        double[] array = benchMark.stats().stream().mapToDouble(Stat::executionTime).sorted().toArray();
        if (array.length % 2 == 0) {
            return (double) (array[array.length / 2 - 1] + array[array.length / 2]) / 2;
        } else return array[array.length / 2];
    }

    private double getStandardDeviation(BenchMark benchMark) {
        double mean = getMean(benchMark);
        double variance = benchMark.stats().stream()
                .mapToDouble(stat -> Math.pow((stat.executionTime() - mean), 2))
                .sum() / (benchMark.stats().size() - 1);
        return Math.sqrt(variance);
    }

}
