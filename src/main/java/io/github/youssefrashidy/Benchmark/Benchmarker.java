package io.github.youssefrashidy.Benchmark;

import io.github.youssefrashidy.ArrayGeneration.ArrayGenerator;
import io.github.youssefrashidy.ArrayGeneration.ArrayType;
import io.github.youssefrashidy.Benchmark.Adaptor.ArrayAdaptor;
import io.github.youssefrashidy.Benchmark.Adaptor.BenchMarkArray;
import io.github.youssefrashidy.Benchmark.Adaptor.BenchMarkArrayGenerator;
import io.github.youssefrashidy.Benchmark.Stat.*;
import io.github.youssefrashidy.Trees.AbstractBST;
import io.github.youssefrashidy.Trees.BST;
import io.github.youssefrashidy.Trees.RBBST;

import java.util.*;

public class Benchmarker {

    static final int WARMUP_ITERATIONS = 3;
    static final int MEASUREMENT_ITERATIONS = 5;
    private final int seed = 1234 ;
    List<Operation> operations = Arrays.asList(Operation.Insertion, Operation.Contains, Operation.Deletion, Operation.TreeSort);

    public List<BenchMark> runBenchMark() {
        ArrayList<BenchMark> results = new ArrayList<>();
        RBBST<Integer, Integer> redBlack = new RBBST<>();
        BST<Integer, Integer> bst = new BST<>();

        ArrayAdaptor generator = new BenchMarkArrayGenerator();
        // Generate Arrays
        ArrayList<BenchMarkArray> arrays = new ArrayList<>();
        arrays.add(generator.getRandomArray(100000));
        arrays.add(generator.getNearlySorted_10(100000));
        arrays.add(generator.getNearlySorted_5(100000));
        arrays.add(generator.getNearlySorted_1(100000));

        // run for RB
        for (var array : arrays) {
            for (var op : operations) {
                BenchMark run = new BenchMark(new ArrayList<>());
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                    runSingleBench(array, redBlack, op);
                    redBlack.clear();
                }
                for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
                    run.add(runSingleBench(array, redBlack, op));
                    redBlack.clear();
                }
                results.add(run);
            }
        }
        for (var array : arrays) {
            for (var op : operations) {
                BenchMark run = new BenchMark(new ArrayList<>());
                for (int i = 0; i < WARMUP_ITERATIONS; i++) {
                    runSingleBench(array, bst, op);
                    bst.clear();
                }
                for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
                    run.add(runSingleBench(array, bst, op));
                    bst.clear();
                }
                results.add(run);
            }
        }
        return results;
    }

    private Stat runSingleBench(BenchMarkArray array, AbstractBST<Integer, Integer, ?> tree, Operation operation) {
        switch (operation) {
            case Insertion -> {
                long time = runInsertion(array.array(), tree);
                int height = tree.getHeight();
                return new Insertion(tree.getClass().getName(), array.inputDistribution(), height, time);
            }
            case Contains -> {
                for (var key : array.array()) tree.insert(key, key);
                int[] testArray = getSortedTest(array.array());
                switch (array.inputDistribution()) {
                    case NEARLY_SORTED_1 -> shuffle(1, testArray);
                    case NEARLY_SORTED_5 -> shuffle(5, testArray);
                    case NEARLY_SORTED_10 -> shuffle(10, testArray);
                    case RANDOM -> shuffle(100, testArray);
                }
                long time = runContains(testArray, tree);
                int height = tree.getHeight();
                return new Contains(tree.getClass().getName(), array.inputDistribution(), height, time);

            }
            case Deletion -> {
                for (var key : array.array()) tree.insert(key, key);
                int[] testArray = getRandomDeletion(array.array(), 20);
                long time = runDeletion(testArray, tree);
                int height = tree.getHeight();
                return new Deletion(tree.getClass().getName(), array.inputDistribution(), height, time);
            }
            case TreeSort -> {
                long time = runTreeSort(array.array(), tree);
                int height = tree.getHeight();
                return new TreeSort(tree.getClass().getName(), array.inputDistribution(), height, time);
            }
            default -> throw new RuntimeException("Invalid operation : " + operation);
        }
    }

    private long runInsertion(int[] array, AbstractBST<Integer, Integer, ?> tree) {
        long startTime = System.nanoTime();
        for (int i : array) {
            tree.insert(i, i);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long runDeletion(int[] array, AbstractBST<Integer, Integer, ?> tree) {
        long startTime = System.nanoTime();
        for (int i : array) {
            tree.delete(i);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long runContains(int[] array, AbstractBST<Integer, Integer, ?> tree) {
        long startTime = System.nanoTime();
        for (int i : array) {
            tree.contains(i);
        }
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private long runTreeSort(int[] array, AbstractBST<Integer, Integer, ?> tree) {
        long startTime = System.nanoTime();
        for (int i : array) {
            tree.insert(i, i);
        }
        var inOrder = tree.inOrder();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private int[] getSortedTest(int[] array) {
        int[] testArray = new int[array.length];
        var sorted = array.clone();
        Arrays.sort(sorted);
        int[] gaps = new int[array.length];
        int count = 0;
        for (int i = 0; i < array.length - 1; i++) {
            if (sorted[i + 1] - sorted[i] > 1) gaps[i] = sorted[i] + 1;
            count++;
        }
        if (count < gaps.length) {
            int max = sorted[array.length - 1];
            int extra = 1;
            for (int i = count; i < gaps.length; i++) {
                gaps[i] = max + extra++;
            }
        }
        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) testArray[i] = sorted[i];
            else testArray[i] = gaps[i];
        }
        return testArray;
    }

    private void shuffle(int percentage, int[] testArray) {
        Random rng = new Random(seed);
        int swaps = percentage * testArray.length / 100;
        for (int i = testArray.length - 1; i > testArray.length - swaps - 1; i--) {
            int index1 = rng.nextInt(0, i + 1);
            int temp = testArray[index1];
            testArray[index1] = testArray[i];
            testArray[i] = temp;
        }
    }

    private int[] getRandomDeletion(int[] array, int percentage) {
        Random rng = new Random(seed);
        int count = percentage * array.length / 100;
        int[] copy = array.clone();
        for (int i = 0; i < count; i++) {
            int index1 = rng.nextInt(0, i + 1);
            int temp = copy[index1];
            copy[index1] = copy[i];
            copy[i] = temp;
        }
        return Arrays.copyOfRange(copy, 0, count);
    }
}
