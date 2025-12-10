package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class ControlsScreen implements Screen {

    private final Game game;
    private SpriteBatch batch;
    private BitmapFont font;

    public ControlsScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(1.8f);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.draw(batch, "CONTROLES", 350, 550);
        font.draw(batch, "WASD = Moverse", 300, 450);
        font.draw(batch, "J = Atacar", 300, 400);
        font.draw(batch, "ESC = Pausa / Menu", 300, 350);
        font.draw(batch, "ENTER = Seleccionar opcion", 300, 300);

        font.draw(batch, "Presiona ESC para volver", 300, 200);

        batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
