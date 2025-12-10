package com.mygdx.game.estructuras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Sistema de estadísticas del juego.
 * Guarda partidas jugadas, ganadas y puntajes (kills).
 * Integra TAD propio (MiLista), QuickSort, búsqueda binaria y persistencia en archivo.
 */
public class StatsManager {

    private static final StatsManager instance = new StatsManager();

    public static StatsManager get() {
        return instance;
    }

    private MiLista<Integer> puntajes = new MiLista<>();
    private int partidasJugadas = 0;
    private int partidasGanadas = 0;

    private StatsManager() {}

    /**
     * Registra el final de una partida.
     * @param gano  si el jugador ganó
     * @param kills cantidad de enemigos eliminados
     */
    public void partidaTerminada(boolean gano, int kills) {
        partidasJugadas++;
        if (gano) partidasGanadas++;

        puntajes.add(kills);
        guardarEnArchivo();
    }

    /**
     * Convierte la lista a arreglo, ordena, y guarda los datos en un archivo local.
     */
    private void guardarEnArchivo() {

        int n = puntajes.size();
        int[] arr = new int[n];
        int idx = 0;

        for (Integer p : puntajes) {
            arr[idx++] = p;
        }

        // Ordena descendente — QuickSort propio
        Algorithms.quickSort(arr);

        // Construye contenido del archivo
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADISTICAS DEL JUEGO ===\n");
        sb.append("Partidas jugadas: ").append(partidasJugadas).append("\n");
        sb.append("Partidas ganadas: ").append(partidasGanadas).append("\n");
        sb.append("------------------------------\n");
        sb.append("Puntajes (ordenados desc):\n");

        for (int value : arr) {
            sb.append(value).append("\n");
        }

        // Ejemplo de búsqueda binaria: ¿existe puntaje perfecto?
        int buscar = 10; // puedes cambiarlo
        int pos = Algorithms.binarySearch(arr, buscar);
        sb.append("\n¿Existen ").append(buscar).append(" kills?: ")
            .append(pos >= 0 ? "Sí" : "No").append("\n");

        // Guardar archivo
        FileHandle fh = Gdx.files.local("stats.txt");
        fh.writeString(sb.toString(), false);
    }
}
