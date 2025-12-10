package com.mygdx.game.controller;

public class LevelManager {

    private int currentLevel = 1;
    private int kills = 0;

    public int getLevel() {
        return currentLevel;
    }

    public int getKills() {
        return kills;
    }

    public void enemyKilled() {
        kills++;
    }

    public boolean shouldAdvanceLevel() {
        return kills >= 10 && currentLevel < 3;
    }

    public void advanceLevel() {
        if (currentLevel < 3) {
            currentLevel++;
            kills = 0;
        }
    }

    public void reset() {
        currentLevel = 1;
        kills = 0;
    }

    public boolean isBossLevel() {
        return currentLevel == 3;
    }
}
