package com.mygdx.game.estructuras;

public class StatsManager {
    private MiLista<Integer> puntajes = new MiLista<>();

    public void agregarPuntaje(int kills) {
        puntajes.add(kills); // Ahora funciona con add()
    }

    public int[] obtenerPuntajesOrdenados() {
        // SOLUCIÓN: Usar toArray() en lugar de for-each
        Object[] arrayObj = puntajes.toArray();
        int[] arr = new int[arrayObj.length];

        for (int i = 0; i < arrayObj.length; i++) {
            arr[i] = (Integer) arrayObj[i];
        }

        // Ordenar (burbuja simple)
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] < arr[j + 1]) { // Orden descendente
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        return arr;
    }

    public int obtenerMejorPuntaje() {
        int[] ordenados = obtenerPuntajesOrdenados();
        return ordenados.length > 0 ? ordenados[0] : 0;
    }
}
