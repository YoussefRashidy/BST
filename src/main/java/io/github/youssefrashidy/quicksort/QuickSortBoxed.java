package io.github.youssefrashidy.quicksort;

import java.util.Random;

public class QuickSortBoxed<K extends Comparable<? super K>> implements Sort{
    private final Random rng = new Random();
    public K[] sort(K[] array) {
        quickSort(array, 0, array.length-1);
        return array ;
    }

    private void quickSort(K[] array, int left, int right) {
        if (left < right) {
            int q = randomizedPartition(array, left, right);
            quickSort(array, left, q - 1);
            quickSort(array, q + 1, right);
        }
    }

    private int randomizedPartition(K[] array, int left, int right) {
        int index = rng.nextInt(left, right + 1);
        K temp = array[right];
        array[right] = array[index];
        array[index] = temp;
        return partition(array, left, right);
    }

    private int partition(K[] array, int left, int right) {
        K pivot = array[right];
        int index = left - 1;
        for (int i = left; i < right; i++) {
            if (array[i].compareTo(pivot) <= 0) {
                index++;
                K temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }
        K temp = array[index + 1];
        array[index+1] = array[right];
        array[right] = temp;
        return index + 1;
    }
}
