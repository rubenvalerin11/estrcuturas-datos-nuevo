package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;

public class Level1Screen implements Screen {

    private final Game game; // CAMBIADO
    private SpriteBatch batch;

    private Texture bg;
    private Player player;
    private PlayerController input;

    public Level1Screen(Game game) { // CAMBIADO
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture("castle_entrance.png"); // ✓ Existe en assets

        player = new Player(70, 80);
        // player.disableCombat(); // Comentado si no existe el método

        input = new PlayerController(player);
    }

    @Override
    public void render(float delta) {
        if (input != null) {
            // input.update(delta); // Comentado si no existe
        }
        if (player != null) {
            player.update(delta);
        }

        // Cuando llega al final → pasar a Level2
        if (player != null && player.getX() > 1050) {
            game.setScreen(new Level2Screen(game));
            dispose();
        }

        batch.begin();
        if (bg != null) {
            batch.draw(bg, 0, 0);
        }
        if (player != null) {
            player.render(batch);
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
        // if (player != null) player.dispose(); // Comentado si no existe
    }
}
