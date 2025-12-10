package com.mygdx.game.estructuras;

public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int size;

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.sig != null) actual = actual.sig;
            actual.sig = nuevo;
        }
        size++;
    }

    public T obtener(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) actual = actual.sig;

        return actual.valor;
    }

    public void eliminar(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

        if (index == 0) cabeza = cabeza.sig;
        else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < index - 1; i++) actual = actual.sig;
            actual.sig = actual.sig.sig;
        }
        size--;
    }

    public int size() { return size; }
}
