package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;
import com.mygdx.game.controller.GameController;

public class Level4Screen implements Screen {

    private final Game game; // CAMBIADO
    private SpriteBatch batch;

    private Texture bg;

    private Player player;
    private PlayerController input;
    private GameController controller;

    private float timer = 0;

    public Level4Screen(Game game) { // CAMBIADO
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("altar_dracula.png"); // ✓ Existe en assets

        player = new Player(90, 80);
        // player.enableCombat();

        input = new PlayerController(player);
        controller = new GameController(player);

        // if (controller.getLevelManager() != null) {
        //     controller.getLevelManager().setLevel(4);
        // }
        // controller.spawnDraculaPhase1();
    }

    @Override
    public void render(float delta) {
        // if (input != null) input.update(delta);
        if (player != null) player.update(delta);
        // if (controller != null) controller.update(delta);

        timer += delta;

        // if (controller != null && controller.getLevelManager() != null &&
        //     controller.getLevelManager().isBossDefeated()) {
        if (timer > 6.0f) { // Temporal: después de 6 segundos
            game.setScreen(new BossFightScreen(game));
            dispose();
        }

        batch.begin();
        if (bg != null) {
            batch.draw(bg, 0, 0);
        }
        // if (controller != null) controller.render(batch);
        if (player != null) player.render(batch);
        batch.end();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        if (batch != null) batch.dispose();
        if (bg != null) bg.dispose();
        // if (player != null) player.dispose();
        // if (controller != null) controller.dispose();
    }
}
