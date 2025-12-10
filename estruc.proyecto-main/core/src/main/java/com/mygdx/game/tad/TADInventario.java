package com.mygdx.game.tad;

import com.mygdx.game.model.Item;
import com.mygdx.game.structures.ListaEnlazada;

public interface TADInventario {

    void agregarItem(Item item);

    void eliminarItem(int id);   // ← AHORA COINCIDE CON Inventario

    Item buscarItem(String nombre);

    void ordenarItemsPorPoder();

    ListaEnlazada<Item> obtenerTodosItems();
}
