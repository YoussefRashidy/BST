package io.github.youssefrashidy.quicksort;

import java.util.Random;

public class QuickSort implements Sort {
    private final Random rng = new Random();
    public int[] sort(int[] array) {
        quickSort(array, 0, array.length-1);
        return array ;
    }

    private void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int q = randomizedPartition(array, left, right);
            quickSort(array, left, q - 1);
            quickSort(array, q + 1, right);
        }
    }

    private int randomizedPartition(int[] array, int left, int right) {
        int index = rng.nextInt(left, right + 1);
        int temp = array[right];
        array[right] = array[index];
        array[index] = temp;
        return partition(array, left, right);
    }

    private int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int index = left - 1;
        for (int i = left; i < right; i++) {
            if (array[i] <= pivot) {
                index++;
                int temp = array[index];
                array[index] = array[i];
                array[i] = temp;
            }
        }
        int temp = array[index + 1];
        array[index+1] = array[right];
        array[right] = temp;
        return index + 1;
    }
}
