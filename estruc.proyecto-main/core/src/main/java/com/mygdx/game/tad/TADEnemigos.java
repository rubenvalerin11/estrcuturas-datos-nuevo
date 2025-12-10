package com.mygdx.game.tad;

import com.mygdx.game.model.Enemy;
import com.mygdx.game.structures.ListaEnlazada;

public interface TADEnemigos {
    void agregarEnemigo(Enemy enemy);
    void eliminarEnemigo(Enemy enemy);
    ListaEnlazada<Enemy> obtenerEnemigos();
}
