package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.player.Player;
import com.mygdx.game.controller.GameController;

public class DraculaCutscene implements Screen {

    private final Game game;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture cutsceneImg;
    private BitmapFont font;

    private float time = 0f;
    private float fade = 1f;  // 1 = negro, 0 = visible

    public DraculaCutscene(Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        batch = new SpriteBatch();
        cutsceneImg = new Texture("draculacutscene.png");

        font = new BitmapFont();
        font.getData().setScale(2f);
    }

    @Override
    public void render(float delta) {
        time += delta;

        // Primero limpiamos pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Zoom lento a la imagen
        float zoom = 1f - Math.min(time * 0.05f, 0.30f);
        camera.zoom = zoom;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibujar fondo (cutscene)
        batch.draw(cutsceneImg, 0, 0, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        // Texto tarda en aparecer
        if (time > 2f) {
            font.draw(batch, "So... you have returned, Alucard...", 200, 200);
        }
        if (time > 4f) {
            font.draw(batch, "Prepare yourself.", 300, 150);
        }

        batch.end();

        // Fade-out en los últimos segundos
        if (time > 6f) fade += delta * 0.5f;
        if (fade > 1f) fade = 1f;

        if (fade > 0) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            batch.begin();
            batch.setColor(0, 0, 0, fade);
            batch.draw(cutsceneImg, 0, 0, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);
            batch.setColor(1, 1, 1, 1);
            batch.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        // Termina la cutscene → Level 4 (Drácula Fase 1)
        if (time > 8f) {
            game.setScreen(new Level4Screen(game));
        }
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        cutsceneImg.dispose();
        font.dispose();
    }
}
