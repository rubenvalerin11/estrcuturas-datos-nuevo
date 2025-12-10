package com.mygdx.game.model;

import com.mygdx.game.structures.ListaEnlazada;

public class Inventario {

    private ListaEnlazada<Item> items;
    private int capacidadMax = 10;

    public Inventario() {
        items = new ListaEnlazada<>();
    }

    public boolean agregarItem(Item item) {
        if (items.tamaño() >= capacidadMax) {
            return false;
        }
        items.agregar(item);
        return true;
    }

    public Item obtenerItem(int index) {
        return items.obtener(index);
    }

    public int cantidadItems() {
        return items.tamaño();
    }

    // *** CORREGIDO ***
    public void eliminarItem(int index) {
        items.eliminar(index);   // eliminar() devuelve void, y ahora eliminarItem también
    }
}
