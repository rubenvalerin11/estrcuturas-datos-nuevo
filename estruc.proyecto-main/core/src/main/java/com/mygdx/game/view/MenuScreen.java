package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;

public class MenuScreen implements Screen {

    private final Game game;

    private Texture background;
    private SpriteBatch batch;
    private BitmapFont font;

    private int selected = 0;
    private final String[] options = {
        "Iniciar Partida",
        "Estadísticas",
        "Controles",
        "Salir"
    };

    public MenuScreen(Game game) {
        this.game = game;

        batch = new SpriteBatch();
        background = new Texture("Menuinicio.png");

        font = new BitmapFont();
        font.getData().setScale(2f);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) selected--;
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) selected++;
        if (selected < 0) selected = options.length - 1;
        if (selected >= options.length) selected = 0;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selected) {
                case 0:
                    game.setScreen(new GameScreen(game));
                    return;
                case 1:
                    game.setScreen(new StatsScreen(game));
                    return;
                case 2:
                    game.setScreen(new ControlsScreen(game));
                    return;
                case 3:
                    Gdx.app.exit();
                    return;
            }
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 960, 640);

        for (int i = 0; i < options.length; i++) {
            float x = 320;
            float y = 350 - i * 50;
            if (i == selected) font.setColor(1, 0.2f, 0.2f, 1);
            else font.setColor(1, 1, 1, 1);

            font.draw(batch, options[i], x, y);
        }

        batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
    }
}
