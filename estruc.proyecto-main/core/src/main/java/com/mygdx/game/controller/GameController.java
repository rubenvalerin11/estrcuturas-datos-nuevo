package com.mygdx.game.controller;

public class GameController {
    private LevelManager levelManager;

    public GameController() {
        this.levelManager = new LevelManager();
    }

    public GameController(Object anything) { // Para compatibilidad
        this();
    }

    public LevelManager getLevelManager() {
        if (levelManager == null) {
            levelManager = new LevelManager();
        }
        return levelManager;
    }

    public void update(float delta) {}
    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {}
    public void dispose() {}

    // Métodos para compilación
    public void spawnSkeleton() {}
    public void spawnMinotauro() {}
    public void spawnDraculaPhase1() {}
    public void spawnDraculaPhase2() {}
}
