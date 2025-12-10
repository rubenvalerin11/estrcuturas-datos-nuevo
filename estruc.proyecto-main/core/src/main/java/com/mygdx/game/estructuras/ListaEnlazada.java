package com.mygdx.game.estructuras;

public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int tamaño;

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.sig != null) actual = actual.sig;
            actual.sig = nuevo;
        }
        tamaño++;
    }

    public T obtener(int i) {
        if (i < 0 || i >= tamaño) throw new IndexOutOfBoundsException();

        Nodo<T> actual = cabeza;
        for (int j = 0; j < i; j++) actual = actual.sig;

        return actual.valor;
    }

    public void eliminar(int i) {
        if (i < 0 || i >= tamaño) throw new IndexOutOfBoundsException();

        if (i == 0) {
            cabeza = cabeza.sig;
        } else {
            Nodo<T> actual = cabeza;
            for (int j = 0; j < i - 1; j++) actual = actual.sig;

            actual.sig = actual.sig.sig;
        }
        tamaño--;
    }

    public int tamaño() {
        return tamaño;
    }
}
