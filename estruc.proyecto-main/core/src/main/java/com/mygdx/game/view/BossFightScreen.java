package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.player.Player;
import com.mygdx.game.controller.GameController;

public class BossFightScreen implements Screen {

    private final Game game; // CAMBIADO
    private SpriteBatch batch;
    private Texture bg;

    private Player player;
    private GameController controller;

    private float timer = 0;

    public BossFightScreen(Game game) { // CAMBIADO
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("boss_room.png"); // ✓ Existe en assets

        player = new Player(90, 80);
        // player.enableCombat();

        controller = new GameController(player);

        // controller.spawnDraculaPhase2();
    }

    @Override
    public void render(float delta) {
        if (player != null) player.update(delta);
        // if (controller != null) controller.update(delta);

        timer += delta;

        batch.begin();
        if (bg != null) {
            batch.draw(bg, 0, 0);
        }

        // if (controller != null) controller.render(batch);
        if (player != null) player.render(batch);

        // Mostrar temporizador
        batch.draw(new Texture("health_bar.png"), 50, 650, 200, 30); // ✓ Existe

        batch.end();

        // Victoria temporal después de 10 segundos
        // if (controller != null && controller.getLevelManager() != null &&
        //     controller.getLevelManager().isBossDefeated()) {
        if (timer > 10.0f) {
            game.setScreen(new WinScreen(game));
            dispose();
        }
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
