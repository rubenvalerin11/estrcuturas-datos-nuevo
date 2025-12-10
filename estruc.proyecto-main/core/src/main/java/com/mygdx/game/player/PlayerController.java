package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController {

    private final Player player;
    private static final float SPEED = 220f;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(float delta) {

        float move = 0f;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            move += SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            move -= SPEED * delta;
        }

        player.move(move);

        // Ataque
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.attack();
        }

        // Cambio de arma
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.setWeapon(Player.WeaponType.MELEE);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.setWeapon(Player.WeaponType.ESPADA1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.setWeapon(Player.WeaponType.ESPADA2);
        }

        // Reset animación (debug opcional)
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            player.resetAnim();
        }
    }
}
