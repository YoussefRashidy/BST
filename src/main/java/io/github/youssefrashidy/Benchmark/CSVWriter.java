package io.github.youssefrashidy.Benchmark;

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
    //TODO add cvs for quick sort comparison
}
