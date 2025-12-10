package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class PauseScreen implements Screen {

    private final MyGdxGame game;
    private final Screen gameScreen; // Guardamos GameScreen para volver
    private final SpriteBatch batch;

    private BitmapFont font;
    private Texture darkOverlay;

    public PauseScreen(MyGdxGame game, Screen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.batch = game.getBatch();

        font = new BitmapFont();
        font.getData().setScale(2f);

        // Fondo semitransparente
        darkOverlay = new Texture("black_80.png");
        // ► CREA una imagen negro 80% transparente (1920x1080 o 800x600).
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(gameScreen); // volver al juego
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(gameScreen); // Resume
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
            game.setScreen(new MenuScreen(game)); // Home
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(darkOverlay, 0, 0);

        font.setColor(Color.WHITE);
        font.draw(batch, "PAUSE", 350, 420);
        font.draw(batch, "PRESS ENTER TO RESUME", 260, 300);
        font.draw(batch, "PRESS BACKSPACE TO RETURN TO MENU", 180, 240);

        batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        darkOverlay.dispose();
        font.dispose();
    }
}
