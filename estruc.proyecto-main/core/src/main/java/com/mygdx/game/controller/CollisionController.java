package com.mygdx.game.controller;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.player.Player;
import com.mygdx.game.model.Enemy;

import java.util.List;

public class CollisionController {

    private final Player player;
    private final List<Enemy> enemigos;

    public CollisionController(Player player, List<Enemy> enemigos) {
        this.player = player;
        this.enemigos = enemigos;
    }

    public void update() {
        Rectangle playerBounds = player.getBounds();
        Rectangle attackBounds = player.getAttackBounds();

        for (Enemy e : enemigos) {
            if (!e.estaVivo()) continue;

            Rectangle enemyBounds = e.getBounds();

            // Colisión cuerpo a cuerpo (enemigo daña al jugador)
            if (playerBounds.overlaps(enemyBounds)) {
                player.receiveDamage(e.getDano());
            }

            // Ataque del jugador (hitbox frontal)
            if (attackBounds.width > 0 && attackBounds.overlaps(enemyBounds)) {
                e.recibirDano(20);
            }
        }
    }
}
