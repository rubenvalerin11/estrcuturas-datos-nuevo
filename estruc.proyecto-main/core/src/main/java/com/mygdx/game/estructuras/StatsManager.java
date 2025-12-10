package com.mygdx.game.estructuras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StatsManager {

    private static final StatsManager instance = new StatsManager();

    public static StatsManager get() {
        return instance;
    }

    private MiLista<Integer> puntajes = new MiLista<>();
    private int partidasJugadas = 0;
    private int partidasGanadas = 0;

    private StatsManager() {}

    public void partidaTerminada(boolean gano, int kills) {
        partidasJugadas++;
        if (gano) partidasGanadas++;
        puntajes.add(kills);
        guardarEnArchivo();
    }

    private void guardarEnArchivo() {
        // pasamos lista a arreglo para aplicar QuickSort
        int n = puntajes.size();
        int[] arr = new int[n];
        int idx = 0;
        for (Integer p : puntajes) {
            arr[idx++] = p;
        }

        Algorithms.quickSort(arr); // ordena descendente

        StringBuilder sb = new StringBuilder();
        sb.append("Partidas jugadas: ").append(partidasJugadas).append("\n");
        sb.append("Partidas ganadas: ").append(partidasGanadas).append("\n");
        sb.append("Puntajes (kills) ordenados desc:\n");
        for (int value : arr) {
            sb.append(value).append("\n");
        }

        FileHandle fh = Gdx.files.local("stats.txt");
        fh.writeString(sb.toString(), false);
    }
}
