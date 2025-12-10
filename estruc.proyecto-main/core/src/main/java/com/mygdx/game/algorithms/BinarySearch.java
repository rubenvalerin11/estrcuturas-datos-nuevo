package com.mygdx.game.algorithms;

import com.mygdx.game.model.Item;

public class BinarySearch {

    public static Item buscarItemPorNombre(String nombre, Item[] items, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = (left + right) / 2;
        int cmp = nombre.compareToIgnoreCase(items[mid].getNombre());

        if (cmp == 0) {
            return items[mid];
        } else if (cmp < 0) {
            return buscarItemPorNombre(nombre, items, left, mid - 1);
        } else {
            return buscarItemPorNombre(nombre, items, mid + 1, right);
        }
    }
}
