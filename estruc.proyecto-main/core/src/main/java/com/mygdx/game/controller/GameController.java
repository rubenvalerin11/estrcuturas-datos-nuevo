package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

public class GameController {

    private static class EnemyNode {
        Enemy enemy;
        EnemyNode next;
        EnemyNode(Enemy enemy) { this.enemy = enemy; }
    }

    private EnemyNode head;
    private int enemyCount;

    private final Player player;
    private final LevelManager levelManager = new LevelManager();

    private float spawnTimer = 0f;
    private float spawnInterval = 2f;

    private boolean bossSpawned = false;

    public GameController(Player player) {
        this.player = player;
    }

    public LevelManager getLevelManager() { return levelManager; }

    private void addEnemy(Enemy enemy) {
        EnemyNode n = new EnemyNode(enemy);
        n.next = head;
        head = n;
        enemyCount++;
    }

    private void clearEnemies() {
        EnemyNode c = head;
        while (c != null) {
            c.enemy.dispose();
            c = c.next;
        }
        head = null;
        enemyCount = 0;
    }

    private boolean hasNoEnemies() { return enemyCount == 0; }

    public void update(float delta) {
        if (!player.isAlive()) return;

        spawnTimer += delta;

        int lvl = levelManager.getLevel();

        if (lvl == 1) {
            // tutorial → no spawnea nada
        } else if (lvl == 2 || lvl == 3) {
            if (spawnTimer >= spawnInterval) {
                spawnTimer = 0f;
                spawnByLevel();
            }
        } else if (lvl == 4) {
            if (!bossSpawned) {
                clearEnemies();
                addEnemy(new BossDracula(500, 200));
                bossSpawned = true;
            }
        }

        EnemyNode curr = head;
        EnemyNode prev = null;

        while (curr != null) {
            Enemy e = curr.enemy;
            e.update(delta, player.getX(), player.getY());

            if (!e.estaVivo()) {
                levelManager.enemyKilled();
                enemyCount--;
                if (prev == null) head = curr.next;
                else prev.next = curr.next;
                curr = (prev == null) ? head : prev.next;
                continue;
            }

            Rectangle rP = player.getBounds();
            Rectangle rE = e.getBounds();

            if (rP.overlaps(rE)) {
                player.receiveDamage(e.getDano());
            }

            Rectangle rAtk = player.getAttackBounds();
            if (rAtk.width > 0 && rAtk.overlaps(rE)) {
                e.recibirDano(20);
            }

            prev = curr;
            curr = curr.next;
        }
    }

    private void spawnByLevel() {
        float x = (float)(Math.random() * 700f + 50f);
        float y = 80f; // en el suelo

        switch (levelManager.getLevel()) {
            case 2:
                addEnemy(new Skeleton(x, y));
                break;
            case 3:
                addEnemy(new Minotauro(x, y));
                break;
        }
    }

    public boolean isBossDefeated() {
        return levelManager.getLevel() == 4 && bossSpawned && hasNoEnemies();
    }

    public void render(SpriteBatch batch) {
        EnemyNode c = head;
        while (c != null) {
            c.enemy.render(batch);
            c = c.next;
        }
    }

    public void resetGame() {
        clearEnemies();
        levelManager.reset();
        bossSpawned = false;
        player.reset();
    }

    public void dispose() {
        clearEnemies();
    }
}
