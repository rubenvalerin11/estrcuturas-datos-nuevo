package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.MyGame;

public class DraculaCutscene implements Screen {

    private final MyGame game;
    private Texture draculaImg;
    private SpriteBatch batch;
    private BitmapFont font;

    private float timer = 0f;

    public DraculaCutscene(MyGame game) {
        this.game = game;
        draculaImg = new Texture("dracula_intro.png"); // imagen estática del jefe
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        timer += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(draculaImg, 250, 150);

        font.getData().setScale(1.6f);
        font.draw(batch, "Dracula:", 400, 500);

        if (timer > 1f) font.draw(batch, "“Mortal insolente...”", 300, 420);
        if (timer > 3f) font.draw(batch, "“Tu viaje termina aquí.”", 300, 350);
        if (timer > 5f) font.draw(batch, "Presiona ENTER para luchar", 260, 250);

        batch.end();

        if (timer > 5f && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game)); // versión boss fight
        }
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); draculaImg.dispose(); }
    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
