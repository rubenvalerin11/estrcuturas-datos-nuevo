package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.CastlevaniaGame;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;

public class Level1Screen implements Screen {

    private final CastlevaniaGame game;
    private SpriteBatch batch;

    private Texture bg;
    private Player player;
    private PlayerController input;

    public Level1Screen(CastlevaniaGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("castle_entrance.png");

        player = new Player(70, 80);
        player.disableCombat(); // NO combate en este nivel

        input = new PlayerController(player);
    }

    @Override
    public void render(float delta) {
        input.update(delta);
        player.update(delta);

        // Cuando llega al final → pasar a Level2
        if (player.getX() > 1050) {
            game.setScreen(new Level2Screen(game));
            dispose();
        }

        batch.begin();
        batch.draw(bg, 0, 0);
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
    }
}
