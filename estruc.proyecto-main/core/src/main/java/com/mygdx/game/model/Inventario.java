package com.mygdx.game.model;

import com.mygdx.game.estructuras.ListaEnlazada;

public class Inventario {
    private ListaEnlazada<Item> items;

    public Inventario() {
        items = new ListaEnlazada<>(); // Debe usar agregar() no add()
    }

    public void agregarItem(Item item) {
        items.agregar(item); // ¡agregar() no add()!
    }

    public Item obtenerItem(int indice) {
        return items.obtener(indice); // ¡obtener() no get()!
    }

    // Resto del código...
}
