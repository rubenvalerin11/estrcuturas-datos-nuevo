package com.mygdx.game.structures;

public class Nodo<T> {
    public T data;
    public Nodo<T> siguiente;

    public Nodo(T data) {
        this.data = data;
        this.siguiente = null;
    }
}
