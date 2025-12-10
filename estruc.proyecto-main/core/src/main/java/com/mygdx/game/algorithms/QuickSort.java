package com.mygdx.game.algorithms;

import com.mygdx.game.model.Item;

public class QuickSort {

    public static void ordenarPorPoder(Item[] items, int low, int high) {
        if (low < high) {
            int pi = particion(items, low, high);
            ordenarPorPoder(items, low, pi - 1);
            ordenarPorPoder(items, pi + 1, high);
        }
    }

    private static int particion(Item[] items, int low, int high) {
        int pivot = items[high].getPoder();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (items[j].getPoder() <= pivot) {
                i++;
                Item temp = items[i];
                items[i] = items[j];
                items[j] = temp;
            }
        }

        Item temp = items[i + 1];
        items[i + 1] = items[high];
        items[high] = temp;

        return i + 1;
    }
}
