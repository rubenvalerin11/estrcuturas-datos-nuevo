package com.mygdx.game.controller;

public class LevelManager {

    private int level = 1;
    private int kills = 0;

    public int getLevel() { return level; }
    public int getKills() { return kills; }

    public void enemyKilled() {
        kills++;
    }

    public int getRequiredKills() {
        switch(level) {
            case 2: return 8;  // pasar a minotauro
            case 3: return 1;  // matar minotauro
        }
        return 0;
    }

    public boolean shouldAdvanceLevel() {
        if (level == 1) return false;
        return kills >= getRequiredKills();
    }

    public void gotoLevel2FromWalk() {
        level = 2;
        kills = 0;
    }

    public void advanceLevel() {
        level++;
        kills = 0;
    }

    public void reset() {
        level = 1;
        kills = 0;
    }
}
