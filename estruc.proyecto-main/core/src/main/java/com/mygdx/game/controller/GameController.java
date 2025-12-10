package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.estructuras.MiLista;
import com.mygdx.game.estructuras.Nodo;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

public class GameController {

    private final Player player;
    private final MiLista<Enemy> enemigos = new MiLista<>();
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

    public Enemy getBoss() {
        Nodo<Enemy> aux = enemigos.getHead();
        while (aux != null) {
            if (aux.valor instanceof BossDracula)
                return aux.valor;
            aux = aux.sig;
        }
        return null;
    }

    public void update(float delta) {

        if (!player.isAlive()) return;

        spawnTimer += delta;

        // Niveles 1 y 2 → enemigos
        if (levelManager.getLevel() < 3) {
            if (spawnTimer >= spawnInterval) {
                spawnTimer = 0f;
                spawnEnemyByLevel();
            }

        } else { // Nivel 3 → solo jefe
            if (!bossSpawned) {
                enemigos.add(new BossDracula(500, 200));
                bossSpawned = true;
            }
        }

        // recorrer lista dinámica
        Nodo<Enemy> nodo = enemigos.getHead();

        while (nodo != null) {
            Enemy e = nodo.valor;
            Nodo<Enemy> sig = nodo.sig;

            e.update(delta, player.getX(), player.getY());

            if (!e.estaVivo()) {
                levelManager.enemyKilled();
                enemigos.remove(e);
                nodo = sig;
                continue;
            }

            Rectangle rPlayer = player.getBounds();
            Rectangle rEnemy = e.getBounds();

            if (rPlayer.overlaps(rEnemy)) {
                player.receiveDamage(e.getDano());
            }

            Rectangle atk = player.getAttackBounds();
            if (atk.width > 0 && atk.overlaps(rEnemy)) {
                e.recibirDano(20);
            }

            nodo = sig;
        }
    }

    private void spawnEnemyByLevel() {
        float x = (float)(Math.random() * 700f + 50f);
        float y = (float)(Math.random() * 300f + 50f);

        switch (levelManager.getLevel()) {
            case 1:
                enemigos.add(new Skeleton(x, y));
                break;
            case 2:
                enemigos.add(new Zombie(x, y));
                break;
        }
    }

    public boolean isBossDefeated() {
        return levelManager.getLevel() == 3 && bossSpawned && enemigos.size() == 0;
    }

    public void render(SpriteBatch batch) {
        for (Enemy e : enemigos)
            e.render(batch);
    }

    public void resetGame() {
        Nodo<Enemy> n = enemigos.getHead();
        while (n != null) {
            n.valor.dispose();
            n = n.sig;
        }
        enemigos.getHead();
        levelManager.reset();
        bossSpawned = false;
        player.reset();
    }

    public void dispose() {
        for (Enemy e : enemigos)
            e.dispose();
    }
}
