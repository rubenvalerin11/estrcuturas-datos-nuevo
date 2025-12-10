package com.mygdx.game.estructuras;

import java.util.Iterator;

public class MiLista<T> implements Iterable<T> {

    private Nodo<T> head;
    private int size;

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

    public boolean remove(T valor) {
        if (head == null) return false;

        if (head.valor == valor) {
            head = head.sig;
            size--;
            return true;
        }

        Nodo<T> anterior = head;
        Nodo<T> actual = head.sig;

        while (actual != null) {
            if (actual.valor == valor) {
                anterior.sig = actual.sig;
                size--;
                return true;
            }
            anterior = actual;
            actual = actual.sig;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public Nodo<T> getHead() {
        return head;
    }

    public boolean contiene(T val) {
        Nodo<T> aux = head;
        while (aux != null) {
            if (aux.valor == val) return true;
            aux = aux.sig;
        }
        return false;
    }

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
                T v = actual.valor;
                actual = actual.sig;
                return v;
            }
        };
    }
}
