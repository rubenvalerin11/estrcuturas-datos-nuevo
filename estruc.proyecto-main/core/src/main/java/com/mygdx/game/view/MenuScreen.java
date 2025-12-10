package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.MyGame;

public class MenuScreen implements Screen {

    private final MyGame game;
    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont optionFont;

    public MenuScreen(MyGame game) {
        this.game = game;
        batch = new SpriteBatch();
        titleFont = new BitmapFont();
        optionFont = new BitmapFont();
        titleFont.setColor(Color.RED);
        optionFont.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        titleFont.getData().setScale(2.2f);
        optionFont.getData().setScale(1.3f);

        titleFont.draw(batch, "Castlevania - Proyecto", 180, 400);
        optionFont.draw(batch, "ENTER - Start Game", 260, 260);
        optionFont.draw(batch, "ESC - Exit (cierra la ventana)", 220, 220);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        optionFont.dispose();
    }
}
