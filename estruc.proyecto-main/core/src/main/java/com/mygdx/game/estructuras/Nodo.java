package com.mygdx.game.estructuras;

public class Nodo<T> {
    public T valor;
    public Nodo<T> sig;

    public Nodo(T valor) {
        this.valor = valor;
        this.sig = null;
    }
}
