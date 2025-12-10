package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.MyGame;

public class VictoryScreen implements Screen {

    private final MyGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    public VictoryScreen(MyGame game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.GOLD);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.getData().setScale(2.2f);
        font.draw(batch, "VICTORIA!", 330, 380);

        font.getData().setScale(1.3f);
        font.draw(batch, "Has derrotado a Dracula", 300, 320);
        font.draw(batch, "Presiona ENTER para volver al menu", 240, 240);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
