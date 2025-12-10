package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.MenuScreen;

public class MyGame extends Game {

    private SpriteBatch batch;
    private int partidasJugadas = 0;
    private int partidasGanadas = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new MenuScreen(this)); // primer pantalla = menú
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    // Estadísticas que te pide el proyecto
    public void registrarPartidaTerminada(boolean gano) {
        partidasJugadas++;
        if (gano) partidasGanadas++;
    }

    public int getPartidasJugadas() {
        return partidasJugadas;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }
}
