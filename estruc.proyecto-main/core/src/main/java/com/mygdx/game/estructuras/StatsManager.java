package com.mygdx.game.estructuras;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class StatsManager {

    private static final StatsManager instance = new StatsManager();
    public static StatsManager get() { return instance; }

    private MiLista<Integer> puntajes = new MiLista<>();
    private int jugadas = 0;
    private int ganadas = 0;

    private StatsManager() {}

    public void registrarPartida(boolean gano, int kills) {
        jugadas++;
        if (gano) ganadas++;

        puntajes.add(kills);
        guardar();
    }

    // compatibilidad con el GameScreen viejo
    public void partidaTerminada(boolean gano, int kills) {
        registrarPartida(gano, kills);
    }

    public int getPartidasJugadas() { return jugadas; }
    public int getPartidasGanadas() { return ganadas; }
    public int getPartidasPerdidas() { return jugadas - ganadas; }

    private void guardar() {

        int[] arr = new int[puntajes.size()];
        int index = 0;
        for (int p : puntajes) arr[index++] = p;

        Algorithms.quickSort(arr);

        StringBuilder sb = new StringBuilder();
        sb.append("Partidas Jugadas: ").append(jugadas).append("\n");
        sb.append("Partidas Ganadas: ").append(ganadas).append("\n");
        sb.append("Kills ordenados: \n");
        for (int v : arr) sb.append(v).append("\n");

        FileHandle fh = Gdx.files.local("stats.txt");
        fh.writeString(sb.toString(), false);
    }
}
