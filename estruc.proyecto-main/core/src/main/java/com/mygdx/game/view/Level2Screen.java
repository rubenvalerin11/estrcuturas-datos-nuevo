package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;
import com.mygdx.game.controller.GameController;

public class Level2Screen implements Screen {

    private final Game game; // CAMBIADO

    private SpriteBatch batch;
    private Texture bg;

    private Player player;
    private PlayerController input;

    private GameController controller;

    private BitmapFont font;

    // Fibonacci spawn times
    private float[] spawnTimes = {1, 1, 2, 3, 5, 8, 13, 21};
    private float timer = 0;
    private int spawnIndex = 0;

    public Level2Screen(Game game) { // CAMBIADO
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("Level1_bg.png"); // ✓ Existe (aunque dice Level1_bg)

        player = new Player(90, 80);
        // player.enableCombat(); // Comentado si no existe

        input = new PlayerController(player);
        controller = new GameController(player);

        // if (controller.getLevelManager() != null) {
        //     controller.getLevelManager().setLevel(2);
        //     controller.getLevelManager().setRequiredKills(8);
        // }

        font = new BitmapFont();
    }

    private void updateSpawn(float delta) {
        timer += delta;

        if (spawnIndex < spawnTimes.length) {
            if (timer >= spawnTimes[spawnIndex]) {
                // controller.spawnSkeleton(); // Comentado si no existe
                spawnIndex++;
            }
        }
    }

    @Override
    public void render(float delta) {

        // if (input != null) input.update(delta);
        if (player != null) player.update(delta);
        // if (controller != null) controller.update(delta);

        updateSpawn(delta);

        // Cuando complete kills → siguiente nivel
        // if (controller != null && controller.getLevelManager() != null &&
        //     controller.getLevelManager().getKills() >= 8) {
        //     game.setScreen(new Level3Screen(game));
        //     dispose();
        // }

        // Lógica temporal: pasar después de 10 segundos
        timer += delta;
        if (timer > 10.0f) {
            game.setScreen(new Level3Screen(game));
            dispose();
        }

        batch.begin();
        if (bg != null) {
            batch.draw(bg, 0, 0);
        }

        // if (controller != null) controller.render(batch);
        if (player != null) player.render(batch);

        if (font != null) {
            font.draw(batch, "Nivel: 2", 850, 570);
            // font.draw(batch, "Kills: " + (controller != null ? controller.getLevelManager().getKills() : 0) + "/8", 850, 540);
            font.draw(batch, "Kills: 0/8", 850, 540); // Temporal
            // font.draw(batch, "Arma: " + player.getWeaponName(), 20, 550);
            font.draw(batch, "Arma: Espada", 20, 550); // Temporal
        }

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
        if (font != null) font.dispose();
    }
}
