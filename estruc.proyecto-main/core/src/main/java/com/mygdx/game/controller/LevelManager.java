package com.mygdx.game.controller;

public class LevelManager {

    private int level;
    private int kills;
    private int requiredKills;

    public LevelManager() {
        this(1);
    }

    public LevelManager(int startLevel) {
        this.level = startLevel;
        this.kills = 0;
        updateRequiredKills();
    }

    private void updateRequiredKills() {
        switch (level) {
            case 2: requiredKills = 8; break;  // esqueletos
            case 3: requiredKills = 1; break;  // minotauro
            case 4: requiredKills = 0; break;  // Drácula => se controla por vida
            default: requiredKills = 0;
        }
    }

    public int getLevel() { return level; }
    public int getKills() { return kills; }
    public int getRequiredKills() { return requiredKills; }

    public void enemyKilled() {
        kills++;
    }

    public boolean shouldAdvanceLevel() {
        if (level == 2 || level == 3) {
            return requiredKills > 0 && kills >= requiredKills;
        }
        return false;
    }

    public void advanceLevel() {
        if (level < 4) {
            level++;
            kills = 0;
            updateRequiredKills();
        }
    }

    // Se llama cuando terminas de caminar el tutorial
    public void gotoLevel2FromWalk() {
        level = 2;
        kills = 0;
        updateRequiredKills();
    }

    public void reset() {
        level = 1;
        kills = 0;
        updateRequiredKills();
    }
}
