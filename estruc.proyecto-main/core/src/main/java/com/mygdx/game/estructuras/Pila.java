package com.mygdx.game.structures;

/**
 * Implementación de una pila enlazada (LIFO).
 */
public class Pila<T> {

    private Nodo<T> cima;
    private int size;

    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.sig = cima;
        cima = nuevo;
        size++;
    }

    public T pop() {
        if (cima == null)
            throw new IllegalStateException("Pila vacía");

        T dato = cima.valor;
        cima = cima.sig;
        size--;
        return dato;
    }

    public T peek() {
        if (cima == null)
            throw new IllegalStateException("Pila vacía");

        return cima.valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int tamaño() {
        return size;
    }
}
