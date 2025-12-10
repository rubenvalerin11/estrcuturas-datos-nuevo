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
    private int count;

    private final Player player;
    private final LevelManager levelManager;

    private BossDracula bossRef;

    // Spawn Fibonacci para esqueletos (nivel 2)
    private float fibTimer;
    private float fibPrev;
    private float fibCurr;
    private float nextSpawnTime;
    private int skeletonsSpawned;
    private static final int MAX_SKELETONS = 8;

    public GameController(Player player) {
        this(player, 1);
    }

    public GameController(Player player, int startLevel) {
        this.player = player;
        this.levelManager = new LevelManager(startLevel);
        onLevelChanged();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void onLevelChanged() {
        clearEnemies();
        bossRef = null;
        resetFib();

        int lvl = levelManager.getLevel();
        switch (lvl) {
            case 1:
                // Tutorial: sin enemigos
                break;
            case 2:
                // Esqueletos: se generan con Fibonacci en update()
                break;
            case 3:
                // Un solo minotauro
                addEnemy(new Minotauro(900, 80));
                break;
            case 4:
                // Drácula jefe final
                bossRef = new BossDracula(900, 80);
                addEnemy(bossRef);
                break;
        }
    }

    private void addEnemy(Enemy e) {
        Node n = new Node(e);
        n.next = head;
        head = n;
        count++;
    }

    private void clearEnemies() {
        Node c = head;
        while (c != null) {
            c.enemy.dispose();
            c = c.next;
        }
        head = null;
        count = 0;
    }

    private void resetFib() {
        fibTimer = 0f;
        fibPrev = 0.5f;
        fibCurr = 0.5f;
        nextSpawnTime = 0f;    // primer esqueleto inmediato
        skeletonsSpawned = 0;
    }

    public float getBossHealthPercent() {
        if (bossRef == null || bossRef.getVidaMaxima() == 0) return 0f;
        return (float) bossRef.getVida() / bossRef.getVidaMaxima();
    }

    public boolean isBossDefeated() {
        return bossRef != null && !bossRef.estaVivo();
    }

    public void update(float delta) {
        int lvl = levelManager.getLevel();

        // Spawn Fibonacci en nivel 2
        if (lvl == 2 && skeletonsSpawned < MAX_SKELETONS) {
            fibTimer += delta;
            if (fibTimer >= nextSpawnTime) {
                float x = 800 + (float) (Math.random() * 200f);
                addEnemy(new Skeleton(x, 80));
                skeletonsSpawned++;

                fibTimer = 0f;
                float next = fibPrev + fibCurr;
                fibPrev = fibCurr;
                fibCurr = next;
                nextSpawnTime = fibCurr;
            }
        }

        Node current = head;
        Node prev = null;

        while (current != null) {
            Enemy e = current.enemy;
            e.update(delta, player.getX(), player.getY());

            if (!e.estaVivo()) {
                levelManager.enemyKilled();
                count--;

                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }

                current = (prev == null) ? head : prev.next;
                continue;
            }

            Rectangle rPlayer = player.getBounds();
            Rectangle rEnemy = e.getBounds();

            if (rPlayer.overlaps(rEnemy)) {
                player.receiveDamage(e.getDano());
            }

            Rectangle rAtk = player.getAttackBounds();
            if (rAtk.width > 0 && rAtk.overlaps(rEnemy)) {
                e.recibirDano(player.getAttackDamage());
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
