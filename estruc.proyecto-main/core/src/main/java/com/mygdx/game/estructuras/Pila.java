package com.mygdx.game.estructuras;

public class Pila<T> {

    private Nodo<T> cima;
    private int tamaño;

    public void push(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.sig = cima;
        cima = nuevo;
        tamaño++;
    }

    public T pop() {
        if (cima == null) throw new IllegalStateException("Pila vacía");

        T dato = cima.valor;
        cima = cima.sig;
        tamaño--;
        return dato;
    }

    public T peek() {
        if (cima == null) throw new IllegalStateException("Pila vacía");
        return cima.valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int tamaño() {
        return tamaño;
    }
}
