package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerController {

    private final Player player;

    public PlayerController(Player p) {
        this.player = p;
    }

    public void handleInput(float delta) {

        float move = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) move = -playerSpeed();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move = playerSpeed();

        player.move(move * delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.attack();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            player.setWeapon(Player.WeaponType.MELEE);
            player.unlockMelee();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            player.setWeapon(Player.WeaponType.ESPADA1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            player.setWeapon(Player.WeaponType.ESPADA2);
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.A) &&
            !Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.resetAnim();
        }
    }

    private float playerSpeed() {
        return 250f;
    }
}
