package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.Benchmark.summary.PairSummary;
import io.github.youssefrashidy.Benchmark.summary.SortComparison;
import io.github.youssefrashidy.Benchmark.summary.StatSummary;

import java.util.List;
import java.util.Locale;

public class BenchmarkPrinter {

    private static final String[] HEADERS = {
            "Operation", "Distribution", "Structure", "Runs", "Height",
            "Min(ms)", "Max(ms)", "Mean(ms)", "Median(ms)", "Std", "Speedup"
    };

    public static void printTable(List<PairSummary> pairs) {
        List<String[]> rows = pairs.stream()
                .flatMap(p -> java.util.stream.Stream.of(p.bst(), p.rb()))
                .map(BenchmarkPrinter::toRow)
                .toList();

        int[] widths = new int[HEADERS.length];
        for (int i = 0; i < HEADERS.length; i++) widths[i] = HEADERS[i].length();
        for (String[] row : rows)
            for (int i = 0; i < row.length; i++)
                widths[i] = Math.max(widths[i], row[i].length());

        String separator = buildSeparator(widths);

        System.out.println(separator);
        System.out.println(buildRow(HEADERS, widths));
        System.out.println(separator);
        for (int i = 0; i < rows.size(); i++) {
            System.out.println(buildRow(rows.get(i), widths));
            if (i % 2 == 1) System.out.println(separator);
        }
    }

    public static void printSortTable(List<SortComparison> comparisons) {
        List<String[]> rows = comparisons.stream()
                .flatMap(c -> java.util.stream.Stream.of(
                        c.BSTSummary(), c.RBTreeSummary(), c.quickSortSummary(), c.quickSortBoxedSummary()
                ))
                .map(BenchmarkPrinter::toRow)
                .toList();

        int[] widths = new int[HEADERS.length];
        for (int i = 0; i < HEADERS.length; i++) widths[i] = HEADERS[i].length();
        for (String[] row : rows)
            for (int i = 0; i < row.length; i++)
                widths[i] = Math.max(widths[i], row[i].length());

        String separator = buildSeparator(widths);

        System.out.println(separator);
        System.out.println(buildRow(HEADERS, widths));
        System.out.println(separator);
        for (int i = 0; i < rows.size(); i++) {
            System.out.println(buildRow(rows.get(i), widths));
            if (i % 4 == 3) System.out.println(separator); // group of 4
        }
    }

    private static String[] toRow(StatSummary s) {
        return new String[]{
                s.operation().toString(),
                s.distribution().toString(),
                s.dataStructure(),
                String.valueOf(s.runs()),
                s.height() < 0 ? "N/A" : String.valueOf(s.height()),
                String.format(Locale.US, "%.3f", s.min() / 1_000_000),
                String.format(Locale.US, "%.3f", s.max() / 1_000_000),
                String.format(Locale.US, "%.3f", s.mean() / 1_000_000),
                String.format(Locale.US, "%.3f", s.median() / 1_000_000),
                String.format(Locale.US, "%.3f", s.std() / 1_000_000),
                s.speedup() < 0 ? "—" : String.format(Locale.US, "%.4fx", s.speedup())
        };
    }

    private static String buildRow(String[] cells, int[] widths) {
        StringBuilder sb = new StringBuilder("| ");
        for (int i = 0; i < cells.length; i++) {
            sb.append(String.format("%-" + widths[i] + "s", cells[i]));
            sb.append(i < cells.length - 1 ? " | " : " |");
        }
        return sb.toString();
    }

    private static String buildSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths) sb.append("-".repeat(w + 2)).append("+");
        return sb.toString();
    }
}