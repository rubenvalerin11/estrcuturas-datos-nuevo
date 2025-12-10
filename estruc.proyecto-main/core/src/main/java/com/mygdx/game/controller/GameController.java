package com.mygdx.game.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.model.*;
import com.mygdx.game.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class GameController {

    private final Player player;
    private final ArrayList<Enemy> enemigos = new ArrayList<>();
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

    public void update(float delta) {

        if (!player.isAlive()) return;

        spawnTimer += delta;

        // NIVEL 1 y 2 → Enemigos normales
        if (levelManager.getLevel() < 3) {
            if (spawnTimer >= spawnInterval) {
                spawnTimer = 0f;
                spawnEnemyByLevel();
            }
        }
        // NIVEL 3 → Solo jefe (Drácula)
        else {
            if (!bossSpawned) {
                enemigos.clear();
                enemigos.add(new BossDracula(500, 200));  // posición fija
                bossSpawned = true;
            }
        }

        // Actualizar enemigos y detectar colisiones
        Iterator<Enemy> it = enemigos.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();

            e.update(delta, player.getX(), player.getY());

            // Enemigo muerto
            if (!e.estaVivo()) {
                levelManager.enemyKilled();
                it.remove();
                continue;
            }

            // Colisión enemigo → daño al jugador
            Rectangle rPlayer = player.getBounds();
            Rectangle rEnemy = e.getBounds();
            if (rPlayer.overlaps(rEnemy)) {
                player.receiveDamage(e.getDano());
            }

            // Colisión ataque del jugador → daño al enemigo
            Rectangle rAttack = player.getAttackBounds();
            if (rAttack.width > 0 && rAttack.overlaps(rEnemy)) {
                e.recibirDano(20); // daño base del jugador
            }
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
        return levelManager.getLevel() == 3 && bossSpawned && enemigos.isEmpty();
    }

    // << NUEVO → Usado por GameScreen para la barra de vida del jefe >>
    public Enemy getBoss() {
        for (Enemy e : enemigos) {
            if (e instanceof BossDracula) {
                return e;
            }
        }
        return null;
    }

    public void render(SpriteBatch batch) {
        for (Enemy e : enemigos)
            e.render(batch);
    }

    public void resetGame() {
        enemigos.clear();
        levelManager.reset();
        bossSpawned = false;
        player.reset();
    }

    public void dispose() {
        for (Enemy e : enemigos)
            e.dispose();
        enemigos.clear();
    }
}
