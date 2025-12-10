package com.mygdx.game.estructuras;

public class ListaEnlazada<T> {

    private class Nodo {
        T dato;
        Nodo siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    private Nodo cabeza;
    private int tamaño;

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    // MÉTODO agregar (para GameStats e Inventario)
    public void agregar(T elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamaño++;
    }

    // MÉTODO add (alias de agregar para StatsManager)
    public void add(T elemento) {
        agregar(elemento);
    }

    // MÉTODO obtener (para Inventario)
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", Tamaño: " + tamaño);
        }

        Nodo actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    // MÉTODO tamaño
    public int tamaño() {
        return tamaño;
    }

    // MÉTODO size (alias para compatibilidad)
    public int size() {
        return tamaño;
    }

    // MÉTODO para convertir a array (para StatsManager)
    public Object[] toArray() {
        Object[] array = new Object[tamaño];
        Nodo actual = cabeza;
        int i = 0;
        while (actual != null) {
            array[i++] = actual.dato;
            actual = actual.siguiente;
        }
        return array;
    }
}
