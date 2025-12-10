package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

public class GameController {

    // ===== Lista dinámica propia =====
    private static class EnemyNode {
        Enemy enemy;
        EnemyNode next;

        EnemyNode(Enemy enemy) {
            this.enemy = enemy;
        }
    }

    private EnemyNode head;   // inicio de la lista
    private int enemyCount;   // cantidad de enemigos en la lista
    // ==================================

    private final Player player;
    private final LevelManager levelManager = new LevelManager();

    private float spawnTimer = 0f;
    private float spawnInterval = 2f;

    private boolean bossSpawned = false;

    public GameController(Player player) {
        this.player = player;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    // --- Operaciones básicas sobre la lista propia ---

    private void addEnemy(Enemy enemy) {
        EnemyNode node = new EnemyNode(enemy);
        node.next = head;
        head = node;
        enemyCount++;
    }

    private void clearEnemies() {
        EnemyNode curr = head;
        while (curr != null) {
            curr.enemy.dispose();
            curr = curr.next;
        }
        head = null;
        enemyCount = 0;
    }

    private boolean hasNoEnemies() {
        return enemyCount == 0;
    }

    // ---------------------------------------------------

    public void update(float delta) {

        if (!player.isAlive()) return;

        spawnTimer += delta;

        // Niveles 1 y 2 → esqueletos / minotauros
        if (levelManager.getLevel() < 3) {
            if (spawnTimer >= spawnInterval) {
                spawnTimer = 0f;
                spawnEnemyByLevel();
            }
        } else {
            // Nivel 3 → solo Drácula
            if (!bossSpawned) {
                clearEnemies();
                addEnemy(new BossDracula(500, 200));
                bossSpawned = true;
            }
        }

        // Actualizar enemigos y detectar colisiones
        EnemyNode curr = head;
        EnemyNode prev = null;

        while (curr != null) {
            Enemy e = curr.enemy;

            e.update(delta, player.getX(), player.getY());

            // Si muere, se elimina de la lista
            if (!e.estaVivo()) {
                levelManager.enemyKilled();
                enemyCount--;

                if (prev == null) {
                    head = curr.next;
                } else {
                    prev.next = curr.next;
                }

                curr = (prev == null) ? head : prev.next;
                continue;
            }

            // daño al jugador
            Rectangle rPlayer = player.getBounds();
            Rectangle rEnemy  = e.getBounds();
            if (rPlayer.overlaps(rEnemy)) {
                player.receiveDamage(e.getDano());
            }

            // ataque corto del jugador (espada)
            Rectangle rAttack = player.getAttackBounds();
            if (rAttack.width > 0 && rAttack.overlaps(rEnemy)) {
                e.recibirDano(25); // daño del jugador (balanceado)
            }

            prev = curr;
            curr = curr.next;
        }
    }

    private void spawnEnemyByLevel() {
        float x = (float)(Math.random() * 700f + 50f);
        float y = (float)(Math.random() * 300f + 50f);

        switch (levelManager.getLevel()) {
            case 1:
                addEnemy(new Skeleton(x, y));
                break;
            case 2:
                addEnemy(new Minotauro(x, y)); // ← CORREGIDO
                break;
            default:
                break;
        }
    }

    public boolean isBossDefeated() {
        return levelManager.getLevel() == 3 && bossSpawned && hasNoEnemies();
    }

    public void render(SpriteBatch batch) {
        EnemyNode curr = head;
        while (curr != null) {
            curr.enemy.render(batch);
            curr = curr.next;
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
