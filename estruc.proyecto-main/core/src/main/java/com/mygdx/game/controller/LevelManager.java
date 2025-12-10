package com.mygdx.game.controller;

import com.mygdx.game.estructuras.ListaEnlazada;

public class LevelManager {
    private int currentLevel = 1;
    private int requiredKills = 0;
    private int currentKills = 0;
    private boolean bossDefeated = false;
    private ListaEnlazada<String> levelLog;

    public LevelManager() {
        this.levelLog = new ListaEnlazada<>();
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }

    public void setRequiredKills(int kills) {
        this.requiredKills = kills;
    }

    public boolean isBossDefeated() {
        return bossDefeated;
    }

    public void setBossDefeated(boolean defeated) {
        this.bossDefeated = defeated;
    }

    public int getKills() {
        return currentKills;
    }

    public void addKill() {
        currentKills++;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
