package com.mygdx.game.structures;

public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamaño;

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.siguiente = nuevo;
            fin = nuevo;
        }
        tamaño++;
    }

    public T desencolar() {
        if (frente == null) {
            throw new IllegalStateException("Cola vacía");
        }
        T dato = frente.data;
        frente = frente.siguiente;

        if (frente == null) fin = null;

        tamaño--;
        return dato;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int tamaño() {
        return tamaño;
    }
}
