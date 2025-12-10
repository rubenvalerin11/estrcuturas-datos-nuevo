package com.mygdx.game.estructuras;

public class Cola<T> {

    private Nodo<T> frente;
    private Nodo<T> fin;
    private int size;

    public void encolar(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);
        if (fin == null) {
            frente = fin = nuevo;
        } else {
            fin.sig = nuevo;
            fin = nuevo;
        }
        size++;
    }

    public T desencolar() {
        if (frente == null) throw new IllegalStateException("Cola vacía");
        T valor = frente.valor;
        frente = frente.sig;
        if (frente == null) fin = null;
        size--;
        return valor;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int size() {
        return size;
    }
}
