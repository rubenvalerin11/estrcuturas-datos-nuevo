package com.mygdx.game.estructuras;

/**
 * Clase con algoritmos fundamentales de ordenamiento y búsqueda.
 * Cumple los requisitos del curso: QuickSort recursivo + búsqueda binaria recursiva.
 */
public class Algorithms {

    // =========================================================
    // QUICK SORT (ORDENAMIENTO DESCENDENTE) — RECURSIVO
    // =========================================================
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;

        int i = left;
        int j = right;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] > pivot) i++;   // Mayor primero (orden desc)
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


    // =========================================================
    // BÚSQUEDA BINARIA (RECURSIVA)
    // =========================================================
    public static int binarySearch(int[] arr, int target) {
        return binarySearch(arr, target, 0, arr.length - 1);
    }

    private static int binarySearch(int[] arr, int target, int left, int right) {
        if (left > right) return -1; // no encontrado

        int mid = (left + right) / 2;

        if (arr[mid] == target) return mid;

        if (target < arr[mid]) {
            // como está en DESCENDENTE, valores menores van a la derecha
            return binarySearch(arr, target, mid + 1, right);
        } else {
            return binarySearch(arr, target, left, mid - 1);
        }
    }
}
