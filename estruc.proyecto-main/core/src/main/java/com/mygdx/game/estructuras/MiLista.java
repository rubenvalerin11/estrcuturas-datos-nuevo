package com.mygdx.game.estructuras;

import java.util.Iterator;

/**
 * Implementación de una lista enlazada simple.
 * Cumple con la estructura dinámica requerida para el proyecto.
 * Soporta inserción, eliminación, búsqueda y recorrido mediante iterador.
 */
public class MiLista<T> implements Iterable<T> {

    private Nodo<T> head;
    private int size = 0;

    /**
     * Agrega un elemento al final de la lista.
     */
    public void add(T valor) {
        Nodo<T> nuevo = new Nodo<>(valor);

        if (head == null) {
            head = nuevo;
        } else {
            Nodo<T> aux = head;
            while (aux.sig != null) aux = aux.sig;
            aux.sig = nuevo;
        }

        size++;
    }

    /**
     * Elimina la primera aparición del valor indicado.
     * @return true si se eliminó, false si no existía.
     */
    public boolean remove(T valor) {
        if (head == null) return false;

        // Caso especial: eliminar primero
        if (head.valor.equals(valor)) {
            head = head.sig;
            size--;
            return true;
        }

        Nodo<T> anterior = head;
        Nodo<T> actual = head.sig;

        while (actual != null) {
            if (actual.valor.equals(valor)) {
                anterior.sig = actual.sig;
                size--;
                return true;
            }
            anterior = actual;
            actual = actual.sig;
        }
        return false;
    }

    /**
     * Búsqueda lineal simple.
     */
    public boolean contiene(T valor) {
        Nodo<T> aux = head;
        while (aux != null) {
            if (aux.valor.equals(valor)) return true;
            aux = aux.sig;
        }
        return false;
    }

    /**
     * Devuelve el tamaño real de la lista.
     */
    public int size() {
        return size;
    }

    /**
     * Permite obtener el nodo inicial si otra estructura requiere recorrer manualmente.
     */
    public Nodo<T> getHead() {
        return head;
    }

    /**
     * Iterador obligatorio para poder usar for-each.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = head;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                T valor = actual.valor;
                actual = actual.sig;
                return valor;
            }
        };
    }
}
