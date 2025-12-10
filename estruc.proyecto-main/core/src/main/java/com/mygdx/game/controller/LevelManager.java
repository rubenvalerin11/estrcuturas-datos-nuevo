package com.mygdx.game.controller;

public class LevelManager {

    private int level = 1;
    private int kills = 0;

    // cuántos enemigos hay que matar por nivel (2–3–4)
    public int getRequiredKills() {
        switch (level) {
            case 2: return 8;   // esqueletos
            case 3: return 10;  // minotauros
            default: return 0;  // tutorial o jefe
        }
    }

    public void enemyKilled() {
        kills++;
    }

    public boolean shouldAdvanceLevel() {
        if (level == 1) return true;              // tutorial → pasa solo
        if (level == 2 || level == 3)
            return kills >= getRequiredKills();
        return false;                             // 4 = jefe final
    }

    public void advanceLevel() {
        if (level < 4) {
            level++;
            kills = 0;
        }
    }

    public int getLevel() { return level; }
    public int getKills() { return kills; }

    public boolean isBossLevel() { return level == 4; }

    public void reset() {
        level = 1;
        kills = 0;
    }
}
