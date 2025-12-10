package com.mygdx.game.view;

import com.badlogic.gdx.Game; // CAMBIADO
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private final Game game; // CAMBIADO
    private Stage stage;
    private SpriteBatch batch;
    private Texture background;

    public MenuScreen(Game game) { // CAMBIADO
        this.game = game;
        this.batch = new SpriteBatch();
        this.background = new Texture("Menuinicio.png"); // ✓ Existe en assets

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Skin temporal si no tienes uiskin.json
        Skin skin = new Skin();
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new com.badlogic.gdx.graphics.g2d.BitmapFont();
        skin.add("default", buttonStyle);

        TextButton startButton = new TextButton("NUEVA PARTIDA", skin);
        TextButton controlsButton = new TextButton("CONTROLES", skin);
        TextButton exitButton = new TextButton("SALIR", skin);

        startButton.addListener(e -> {
            game.setScreen(new Level1Screen(game));
            dispose();
            return true;
        });

        controlsButton.addListener(e -> {
            game.setScreen(new ControlsScreen(game));
            dispose();
            return true;
        });

        exitButton.addListener(e -> {
            Gdx.app.exit();
            return true;
        });

        table.add(startButton).padBottom(20).row();
        table.add(controlsButton).padBottom(20).row();
        table.add(exitButton).padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (background != null) {
            batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        if (background != null) background.dispose();
    }
}
