package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.Game;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.model.BossDracula;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.player.Player;
import com.mygdx.game.estructuras.StatsManager;

public class GameScreen implements Screen {

    private final Game game;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture background;
    private Texture healthBar;
    private Texture bossHealthBar;

    private BitmapFont font;

    private Player player;
    private GameController controller;

    public GameScreen(Game game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        batch = new SpriteBatch();

        background = new Texture("Level1_bg.png");
        healthBar = new Texture("health_bar.png");
        bossHealthBar = new Texture("boss_health.png");

        font = new BitmapFont();
        font.getData().setScale(1.6f);

        player = new Player(100, 80f);
        controller = new GameController(player);
    }

    @Override
    public void render(float delta) {

        // Pausa
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }

        // actualizar lógica
        player.update(delta);
        controller.update(delta);

        LevelManager lm = controller.getLevelManager();

        // avanzar nivel
        if (lm.shouldAdvanceLevel()) {
            lm.advanceLevel();
        }

        // jefe derrotado → victoria
        if (controller.isBossDefeated()) {
            StatsManager.get().partidaTerminada(true, lm.getKills());
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // jugador muerto → game over
        if (!player.isAlive()) {
            StatsManager.get().partidaTerminada(false, lm.getKills());
            game.setScreen(new GameOverScreen(game));
            return;
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        player.render(batch);
        controller.render(batch);

        // Barra de vida jugador
        float p = player.getHealthPercent();
        batch.draw(healthBar, 20, 580, healthBar.getWidth() * p, healthBar.getHeight());

        // Texto nivel + kills
        font.draw(batch, "Nivel: " + lm.getLevel(), 800, 620);
        font.draw(batch, "Kills: " + lm.getKills() + "/" + lm.getRequiredKills(), 750, 590);

        // Texto tutorial (solo nivel 1)
        if (lm.getLevel() == 1) {
            font.draw(batch, "NIVEL 1 - TUTORIAL", 350, 350);
            font.draw(batch, "Moverse: W A S D", 350, 320);
            font.draw(batch, "Atacar: J", 350, 290);
            font.draw(batch, "Pausa: ESC", 350, 260);
        }

        // Barra de vida del jefe
        if (lm.isBossLevel()) {
            // hay un solo enemigo, el Boss
            // tu GameController ya se encarga de tenerlo
            // aquí solo dibujamos la barra si existe
            // (simplificamos: no buscamos el objeto, solo cuando level 4 y aún no victory)
            // si quieres exacto, guarda referencia al Boss en el controller
            font.draw(batch, "DRACULA", 380, 560);
            // puedes adaptar luego a porcentaje real usando getters del boss
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
        healthBar.dispose();
        bossHealthBar.dispose();
        font.dispose();
        controller.dispose();
    }
}
