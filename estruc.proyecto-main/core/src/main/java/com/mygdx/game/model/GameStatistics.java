package com.mygdx.game.model;

import java.io.*;

public class GameStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    private int partidasJugadas;
    private int partidasGanadas;
    private int enemigosDerrotados;
    private int tiempoTotalJuego; // en segundos

    public void incrementarPartidasJugadas() { partidasJugadas++; }
    public void incrementarPartidasGanadas() { partidasGanadas++; }
    public void agregarEnemigosDerrotados(int cantidad) { enemigosDerrotados += cantidad; }
    public void agregarTiempoJuego(int segundos) { tiempoTotalJuego += segundos; }

    public void guardarEstadisticas(String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameStatistics cargarEstadisticas(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (GameStatistics) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Si no existe, devolvemos estadísticas nuevas
            return new GameStatistics();
        }
    }

    @Override
    public String toString() {
        return "Partidas jugadas: " + partidasJugadas +
            "\nPartidas ganadas: " + partidasGanadas +
            "\nEnemigos derrotados: " + enemigosDerrotados +
            "\nTiempo total de juego: " + tiempoTotalJuego + " s";
    }
}
