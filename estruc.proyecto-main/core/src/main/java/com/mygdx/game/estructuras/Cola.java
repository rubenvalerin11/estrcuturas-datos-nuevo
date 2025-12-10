package com.mygdx.game.estructuras;

public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamaño;

    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.sig = nuevo;
            fin = nuevo;
        }
        tamaño++;
    }

    public T desencolar() {
        if (frente == null) throw new IllegalStateException("Cola vacía");

        T dato = frente.valor;
        frente = frente.sig;

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
