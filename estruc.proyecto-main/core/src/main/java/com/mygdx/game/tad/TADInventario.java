package com.mygdx.game.tad;

import com.mygdx.game.model.Item;
import com.mygdx.game.estructuras.ListaEnlazada;

public interface TADInventario {

    void agregarItem(Item item);

    Item buscarItem(String nombre);

    ListaEnlazada<Item> obtenerTodosItems();
}
