package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.MainGame;

public class PauseScreen implements Screen {

    private final MainGame game;
    private final Screen returnTo; // para volver al juego

    private SpriteBatch batch;
    private Texture dimBackground;
    private BitmapFont font;

    public PauseScreen(MainGame game, Screen returnTo) {
        this.game = game;
        this.returnTo = returnTo;
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(2.2f);

        dimBackground = createDimTexture();
    }

    private Texture createDimTexture() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(0, 0, 0, 0.75f); // negro 75% opaco
        pm.fill();
        Texture t = new Texture(pm);
        pm.dispose();
        return t;
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(returnTo);
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.M)) {
            game.setScreen(new MenuScreen(game));
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.C)) {
            game.setScreen(new ControlsScreen(game, this));
        }

        batch.begin();

        batch.draw(dimBackground, 0, 0, 960, 640);

        font.draw(batch, "PAUSED", 400, 500);
        font.draw(batch, "[ESC] → Volver al juego", 300, 360);
        font.draw(batch, "[M] → Menu principal", 300, 300);
        font.draw(batch, "[C] → Controles", 300, 240);

        batch.end();
    }

    @Override public void dispose() {
        batch.dispose();
        dimBackground.dispose();
        font.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
