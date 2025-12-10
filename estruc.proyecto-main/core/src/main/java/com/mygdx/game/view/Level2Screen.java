package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.CastlevaniaGame;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;
import com.mygdx.game.controller.GameController;

public class Level2Screen implements Screen {

    private final CastlevaniaGame game;

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

    public Level2Screen(CastlevaniaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("Level1_bg.png");

        player = new Player(90, 80);
        player.enableCombat(); // combate activado

        input = new PlayerController(player);
        controller = new GameController(player);

        controller.getLevelManager().setLevel(2);
        controller.getLevelManager().setRequiredKills(8);

        font = new BitmapFont();
    }

    private void updateSpawn(float delta) {
        timer += delta;

        if (spawnIndex < spawnTimes.length) {
            if (timer >= spawnTimes[spawnIndex]) {
                controller.spawnSkeleton();
                spawnIndex++;
            }
        }
    }

    @Override
    public void render(float delta) {

        input.update(delta);
        player.update(delta);
        controller.update(delta);

        updateSpawn(delta);

        // Cuando complete kills → siguiente nivel
        if (controller.getLevelManager().getKills() >= 8) {
            game.setScreen(new Level3Screen(game));
            dispose();
        }

        batch.begin();
        batch.draw(bg, 0, 0);

        controller.render(batch);
        player.render(batch);

        font.draw(batch, "Nivel: 2", 850, 570);
        font.draw(batch, "Kills: " + controller.getLevelManager().getKills() + "/8", 850, 540);
        font.draw(batch, "Arma: " + player.getWeaponName(), 20, 550);

        batch.end();
    }

    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        bg.dispose();
        player.dispose();
        controller.dispose();
        font.dispose();
    }
}
