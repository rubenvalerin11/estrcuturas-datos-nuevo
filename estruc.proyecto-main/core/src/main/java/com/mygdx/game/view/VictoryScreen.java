package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MainGame;

public class VictoryScreen implements Screen {

    private final MainGame game;
    private SpriteBatch batch;

    private Texture background;
    private Texture pressEnter;

    public VictoryScreen(MainGame game) {
        this.game = game;
        this.batch = new SpriteBatch();

        background = new Texture("victory_bg.png"); // usa tu imagen
        pressEnter = new Texture("press_start.png");
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game));
        }

        batch.begin();
        batch.draw(background, 0, 0, 960, 640);
        batch.draw(pressEnter, 350, 100);
        batch.end();
    }

    @Override public void dispose() {
        batch.dispose();
        background.dispose();
        pressEnter.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
