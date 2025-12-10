package com.mygdx.game.estructuras;

public class Algorithms {

    // QuickSort recursivo (cumple requisito de recursividad + ordenamiento)
    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return; // caso base

        int i = left;
        int j = right;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] > pivot) i++;  // mayor primero
            while (arr[j] < pivot) j--;

            if (i <= j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }
        }

        if (left < j) quickSort(arr, left, j);
        if (i < right) quickSort(arr, i, right);
    }
}
