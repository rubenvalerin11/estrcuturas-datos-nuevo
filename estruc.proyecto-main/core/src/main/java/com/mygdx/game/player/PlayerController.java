package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.model.WeaponType; // IMPORT CORRECTO

public class PlayerController {
    private final Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void handleInput(float delta) {
        float speed = 200 * delta;

        // Movimiento
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosition(player.getX() - speed, player.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosition(player.getX() + speed, player.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosition(player.getX(), player.getY() + speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosition(player.getX(), player.getY() - speed);
        }

        // Cambio de armas (CORREGIDO)
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            // player.setWeapon(WeaponType.MELEE); // Comenta si no tienes este método
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // player.setWeapon(WeaponType.ESPADA1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            // player.setWeapon(WeaponType.ESPADA2);
        }

        // Ataque
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            // player.attack();
        }

        // Salto
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            // player.jump();
        }
    }

    public void update(float delta) {
        handleInput(delta);
        player.update(delta);
    }
}
