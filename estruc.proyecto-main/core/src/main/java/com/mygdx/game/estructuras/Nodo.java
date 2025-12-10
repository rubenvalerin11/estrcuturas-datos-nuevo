package com.mygdx.game.estructuras;

/**
 * Nodo genérico para la lista enlazada simple.
 * Contiene un valor y una referencia al siguiente nodo.
 */
public class Nodo<T> {

    T valor;         // dato almacenado
    Nodo<T> sig;     // referencia al siguiente nodo

    public Nodo(T valor) {
        this.valor = valor;
        this.sig = null;
    }

    public T getValor() {
        return valor;
    }

    public Nodo<T> getSiguiente() {
        return sig;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.sig = siguiente;
    }
}
