package io.github.youssefrashidy.ArrayGeneration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ArrayGenerator {
    private final Random rand ;
    private final int DEFAULT_MAX = 10000 ;

    public ArrayGenerator() {
        this.rand = new Random() ;
    }
    public ArrayGenerator(long seed) {
        this.rand = new Random(seed) ;
    }
    public int[] randomArray(int size , int max) {
        int[] arr = new int[size] ;
        for (int i = 0; i < size ; i++) arr[i] = rand.nextInt(0,max) ;
        return arr ;
    }

    public int[] randomArray(int size) {
        return  randomArray(size, size*10) ;
    }

    public int[] sortedArray(int size , int max) {
        int[] arr = randomArray(size,max) ;
        Arrays.sort(arr) ;
        return arr ;
    }

    public int[] sortedArray(int size ) {
        return sortedArray(size, DEFAULT_MAX) ;
    }

    public int[] inverselySortedArray(int size ,int max) {
        int[] arr = randomArray(size , max) ;
        return Arrays.stream(arr)
                .map(num -> -num)
                .sorted().map(num -> -num)
                .toArray() ;
    }
    public int[] inverselySortedArray(int size) {
        return inverselySortedArray(size, DEFAULT_MAX) ;
    }

    public int[] nearlySortedArray(int size, int swaps) {
        int[] arr = sortedArray(size);
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(size);
            int b = rand.nextInt(size);
            int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
        }
        return arr;
    }

    public int[] nearlySortedArray(int size) {
        return nearlySortedArray(size, Math.max(1, size / 10));
    }

    public ArrayList<int[]> generateBatch(int numOfArrays) {
        var arrays = new ArrayList<int[]>(numOfArrays) ;
        int min = 100 ;
        int max = 10000 ;
        for (int i = 0; i < numOfArrays ; i++) {
            int size = rand.nextInt(max-min+1) +min ;
            ArrayType type = ArrayType.values()[rand.nextInt(ArrayType.values().length)] ;
            int[] arr = switch (type) {
                case SORTED -> sortedArray(size) ;
                case INVERSELY_SORTED -> inverselySortedArray(size) ;
                case RANDOM -> randomArray(size) ;
//                case NEARLY_SORTED -> nearlySortedArray(size) ;
                default -> throw new RuntimeException("Unknown Array type") ;
            } ;
            arrays.add(arr) ;
        }
        return arrays ;
    }

    public ArrayList<int[]> generateBatch(ArrayList<Integer> sizes , ArrayType[] modes , int max) {
        ArrayList<int[]> arrays = new ArrayList<>() ;
        for (int size : sizes) {
            for (ArrayType mode : modes) {
                arrays.add(switch (mode){
                    case RANDOM -> randomArray(size, max) ;
                    case SORTED -> sortedArray(size,max) ;
                    case INVERSELY_SORTED -> inverselySortedArray(size, max) ;
//                    case NEARLY_SORTED -> nearlySortedArray(size,max) ;
                    default -> throw new IllegalArgumentException("Unsupported Array type") ;
                }) ;
            }
        }
        return arrays ;
    }
}
