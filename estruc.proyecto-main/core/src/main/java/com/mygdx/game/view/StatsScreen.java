package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.mygdx.game.estructuras.StatsManager;

public class StatsScreen implements Screen {

    private final Game game;
    private SpriteBatch batch;
    private BitmapFont font;

    public StatsScreen(Game game) {
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

        StatsManager sm = StatsManager.get();

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        font.draw(batch, "ESTADISTICAS", 320, 550);

        font.draw(batch, "Partidas jugadas: " + sm.getPartidasJugadas(), 300, 450);
        font.draw(batch, "Ganadas: " + sm.getPartidasGanadas(), 300, 400);
        font.draw(batch, "Perdidas: " + sm.getPartidasPerdidas(), 300, 350);

        font.draw(batch, "Presiona ESC para volver", 300, 250);

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
