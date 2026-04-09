package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.Benchmark.summary.PairSummary;
import io.github.youssefrashidy.Benchmark.summary.SortComparison;
import io.github.youssefrashidy.Benchmark.summary.StatSummary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    public String writeTreeCVS(List<PairSummary> summaryList) {
        StringBuilder builder = new StringBuilder();
        builder.append(StatSummary.csvHeader());
        builder.append(System.lineSeparator());
        for (PairSummary summary : summaryList) {
            builder.append(summary.toString());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
    public boolean writeTreeCSV(List<PairSummary> summaryList , String path) {
        String csv = writeTreeCVS(summaryList) ;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(csv);
            writer.close();
        } catch (IOException e) {
            return false ;
        }
        return true ;
    }
    //TODO add cvs for quick sort comparison
    public String writeSortCSV(List<SortComparison> summaryList) {
        StringBuilder builder = new StringBuilder();
        builder.append(StatSummary.csvHeader());
        builder.append(System.lineSeparator());
        for (SortComparison summary : summaryList) {
            builder.append(summary.BSTSummary()).append(System.lineSeparator());
            builder.append(summary.RBTreeSummary()).append(System.lineSeparator());
            builder.append(summary.quickSortSummary()).append(System.lineSeparator());
            builder.append(summary.quickSortBoxedSummary()).append(System.lineSeparator());
        }
        return builder.toString();
    }
    public boolean writeSortCSV(List<SortComparison> summaryList , String path) {
        String csv = writeSortCSV(summaryList) ;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(csv);
            writer.close();
        } catch (IOException e) {
            return false ;
        }
        return true ;
    }
}
