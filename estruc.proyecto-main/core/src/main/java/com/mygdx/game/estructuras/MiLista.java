package com.mygdx.game.estructuras;

public class MiLista<T> extends ListaEnlazada<T> {
    // Hereda todos los métodos de ListaEnlazada

    // Constructor
    public MiLista() {
        super();
    }

    // Método adicional si es necesario
    public boolean estaVacia() {
        return tamaño() == 0;
    }
}
