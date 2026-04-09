package io.github.youssefrashidy;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import io.github.youssefrashidy.Benchmark.*;
import io.github.youssefrashidy.treeshell.TreeShell;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose one of the following options");
            System.out.println("1.Tree Shell");
            System.out.println("2.Tree benchmark");
            System.out.println("3.Sort benchmark");
            int option = -1;
            try {
                option = scanner.nextInt();
                scanner.nextLine() ; // To consume remaining \n
            } catch (RuntimeException e) {
                System.out.println("Invalid option");
            }
            switch (option) {
                case 1 -> {
                    TreeShell shell = new TreeShell();
                    shell.shell();
                }
                case 2 -> {
                    System.out.println("Input file output name/path (Default results.csv) : ");
                    String path = scanner.nextLine();
                    if (path.isEmpty()) path = "results.csv";
                    if (!path.endsWith(".csv")) path += ".csv";
                    Benchmarker benchmarker = new Benchmarker();
                    var results = benchmarker.runBenchMark();
                    FeatureExtractor featureExtractor = new FeatureExtractor();
                    var features = featureExtractor.extractFeatures(results);
                    CSVWriter csvWriter = new CSVWriter();
                    if (csvWriter.writeTreeCSV(features, path)) {
                        System.out.println("CSV written to : " + Path.of(path).toAbsolutePath());
                        BenchmarkPrinter.printTable(features);
                    } else {
                        System.out.println("Failed to write results to CSV file.");
                    }

                }
                case 3 -> {
                    System.out.print("Input file output name/path : ");
                    String path = scanner.nextLine();
                    if (path.isEmpty()) path = "results.csv";
                    if (!path.endsWith(".csv")) path += ".csv";
                    SortBenchMarker sortBenchMarker = new SortBenchMarker();
                    var results = sortBenchMarker.runSortBenchmark();
                    var features = new FeatureExtractor().extractSortFeatures(results);
                    CSVWriter csvWriter = new CSVWriter();
                    if (csvWriter.writeSortCSV(features, path)) {
                        System.out.println("CSV written to : " + Path.of(path).toAbsolutePath());
                        BenchmarkPrinter.printSortTable(features);
                    } else {
                        System.out.println("Failed to write results to CSV file.");
                    }

                }
                default -> System.out.println("Invalid option");

            }


        }
    }
}