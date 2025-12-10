package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.CastlevaniaGame;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;
import com.mygdx.game.controller.GameController;

public class Level4Screen implements Screen {

    private final CastlevaniaGame game;
    private SpriteBatch batch;

    private Texture bg;

    private Player player;
    private PlayerController input;
    private GameController controller;

    public Level4Screen(CastlevaniaGame game) {
        this.game = game;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        bg = new Texture("altar_dracula.png");

        player = new Player(90, 80);
        player.enableCombat();

        input = new PlayerController(player);
        controller = new GameController(player);

        controller.getLevelManager().setLevel(4);
        controller.spawnDraculaPhase1();
    }

    @Override
    public void render(float delta) {

        input.update(delta);
        player.update(delta);
        controller.update(delta);

        if (controller.getLevelManager().isBossDefeated()) {
            game.setScreen(new BossFightScreen(game));
            dispose();
        }

        batch.begin();
        batch.draw(bg, 0, 0);
        controller.render(batch);
        player.render(batch);
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
    }
}
