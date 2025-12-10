package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.BossDracula;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.model.Minotauro;
import com.mygdx.game.model.Skeleton;
import com.mygdx.game.player.Player;

public class GameController {

    private static class Node {
        Enemy enemy;
        Node next;
        Node(Enemy e) { this.enemy = e; }
    }

    private Node head;
    private final Player player;
    private final LevelManager levelManager;

    // Fibonacci spawns (Nivel 2)
    private float fibTimer;
    private float fibPrev;
    private float fibCurr;
    private float nextSpawnTime;
    private int skeletonsSpawned;
    private static final int MAX_SKELETONS = 8;

    private BossDracula bossRef;

    public GameController(Player player, int startLevel) {
        this.player = player;
        this.levelManager = new LevelManager(startLevel);
        onLevelChanged();
    }

    public LevelManager getLevelManager() { return levelManager; }

    public void onLevelChanged() {
        clearEnemies();
        bossRef = null;
        resetFib();

        switch (levelManager.getLevel()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                addEnemy(new Minotauro(900, 80));
                break;
            case 4:
                bossRef = new BossDracula(900, 80);
                addEnemy(bossRef);
                break;
        }
    }

    private void addEnemy(Enemy e) {
        Node n = new Node(e);
        n.next = head;
        head = n;
    }

    private void clearEnemies() {
        Node c = head;
        while (c != null) {
            c.enemy.dispose();
            c = c.next;
        }
        head = null;
    }

    private void resetFib() {
        fibTimer = 0f;
        fibPrev = 0.5f;
        fibCurr = 0.5f;
        nextSpawnTime = 0f;
        skeletonsSpawned = 0;
    }

    public boolean isBossDefeated() {
        return bossRef != null && !bossRef.isAlive();
    }

    public void update(float delta) {

        int lvl = levelManager.getLevel();

        // NIVEL 2 – Esqueletos Fibonacci
        if (lvl == 2 && skeletonsSpawned < MAX_SKELETONS) {
            fibTimer += delta;
            if (fibTimer >= nextSpawnTime) {

                addEnemy(new Skeleton(900 + (float)(Math.random()*100), 80));
                skeletonsSpawned++;

                fibTimer = 0f;
                float next = fibPrev + fibCurr;
                fibPrev = fibCurr;
                fibCurr = next;
                nextSpawnTime = fibCurr;
            }
        }

        // UPDATE DE CADA ENEMIGO
        Node current = head;
        Node prev = null;

        while (current != null) {
            Enemy e = current.enemy;
            e.update(delta, player.getX(), player.getY());

            if (!e.isAlive()) {
                levelManager.enemyKilled();

                if (prev == null) head = current.next;
                else prev.next = current.next;

                current = (prev == null) ? head : prev.next;
                continue;
            }

            // Colisión enemigo → daño
            Rectangle p = player.getBounds();
            Rectangle r = e.getBounds();
            if (p.overlaps(r)) {
                player.receiveDamage(e.getDamage());
            }

            // Ataque del jugador
            Rectangle atk = player.getAttackBounds();
            if (atk.width > 0 && atk.overlaps(r)) {
                e.receiveDamage(player.getAttackDamage());
            }

            prev = current;
            current = current.next;
        }
    }

    public void render(SpriteBatch batch) {
        Node c = head;
        while (c != null) {
            c.enemy.render(batch);
            c = c.next;
        }
    }

    public void dispose() {
        clearEnemies();
    }
}
