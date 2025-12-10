package com.mygdx.game;

import com.mygdx.game.estructuras.ListaEnlazada;

public class GameStats {
    private ListaEnlazada<Integer> scores;

    public GameStats() {
        this.scores = new ListaEnlazada<>();
    }

    public void addScore(int score) {
        scores.agregar(score); // ¡Usar agregar()!
    }

    // Resto del código...
}
