package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DraculaCutscene implements Screen {

    private final Game game; // CAMBIADO
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture cutsceneImg;
    private float time = 0;

    public DraculaCutscene(Game game) { // CAMBIADO
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        batch = new SpriteBatch();

        cutsceneImg = new Texture("draculactiscene.png"); // ✓ Existe (draculactiscene)
    }

    @Override
    public void render(float delta) {
        time += delta;

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        if (cutsceneImg != null) {
            batch.draw(cutsceneImg, 0, 0, 1280, 720);
        }
        batch.end();

        if (time > 3.0f) {
            game.setScreen(new Level4Screen(game));
            dispose();
        }
    }

    @Override public void resize(int width, int height) { viewport.update(width, height); }
    @Override public void show() { time = 0; }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() {
        if (batch != null) batch.dispose();
        if (cutsceneImg != null) cutsceneImg.dispose();
    }
}
