package io.github.youssefrashidy.Benchmark.summary;

import io.github.youssefrashidy.ArrayGeneration.ArrayType;
import io.github.youssefrashidy.Benchmark.Operation;

import java.util.Locale;

public record StatSummary(long runs, double min, double max, double mean, double median, double std, int height,
                          String dataStructure, ArrayType distribution, Operation operation, double speedup) {

    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s,%s,%s,%d,%d,%.3f,%.3f,%.3f,%.3f,%.3f,%.6f",
                operation,
                distribution,
                dataStructure,
                runs,
                height,
                min,
                max,
                mean,
                median,
                std,
                speedup
        );
    }

    public static String csvHeader() {
        return "operation,distribution,dataStructure,runs,height,min,max,mean,median,std,speedup";
    }
}
