package com.mygdx.game.model;

import com.mygdx.game.estructuras.ListaEnlazada;

public class Inventario {

    private ListaEnlazada<Item> items;

    public Inventario() {
        items = new ListaEnlazada<>();
    }

    public void agregarItem(Item item) {
        items.agregar(item);
    }

    public Item buscarItem(String nombre) {
        for (int i = 0; i < items.size(); i++) {
            Item it = items.obtener(i);
            if (it.getNombre().equals(nombre)) return it;
        }
        return null;
    }

    public ListaEnlazada<Item> obtenerTodos() {
        return items;
    }
}
