package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseScreen implements Screen {

    private final Game game; // CAMBIADO
    private Stage stage;
    private SpriteBatch batch;
    private Texture pauseTexture;

    public PauseScreen(Game game) { // CAMBIADO
        this.game = game;
        this.batch = new SpriteBatch();
        this.pauseTexture = new Texture("pause_background.png"); // O usar color sólido

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton resumeButton = new TextButton("REANUDAR", skin);
        TextButton menuButton = new TextButton("MENÚ PRINCIPAL", skin);
        TextButton exitButton = new TextButton("SALIR", skin);

        resumeButton.addListener(e -> {
            if (e.toString().contains("touchUp")) {
                // Volver a la pantalla anterior (necesitarías guardar referencia)
                // Por ahora, vamos al menú
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
            return false;
        });

        menuButton.addListener(e -> {
            if (e.toString().contains("touchUp")) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
            return false;
        });

        exitButton.addListener(e -> {
            if (e.toString().contains("touchUp")) {
                Gdx.app.exit();
                return true;
            }
            return false;
        });

        table.add(resumeButton).padBottom(20).row();
        table.add(menuButton).padBottom(20).row();
        table.add(exitButton).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (pauseTexture != null) {
            batch.draw(pauseTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() {
        if (stage != null) stage.dispose();
        if (batch != null) batch.dispose();
        if (pauseTexture != null) pauseTexture.dispose();
    }
}
