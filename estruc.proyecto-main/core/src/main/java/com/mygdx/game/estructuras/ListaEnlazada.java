package com.mygdx.game.structures;

/**
 * Lista enlazada simple compatible con Nodo<T>.
 */
public class ListaEnlazada<T> {

    private Nodo<T> cabeza;
    private int size;

    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.sig != null) {
                actual = actual.sig;
            }
            actual.sig = nuevo;
        }
        size++;
    }

    public T obtener(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);

        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++)
            actual = actual.sig;

        return actual.valor;
    }

    public void eliminar(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);

        if (index == 0) {
            cabeza = cabeza.sig;
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < index - 1; i++)
                actual = actual.sig;

            actual.sig = actual.sig.sig;
        }
        size--;
    }

    public int tamaño() {
        return size;
    }
}
