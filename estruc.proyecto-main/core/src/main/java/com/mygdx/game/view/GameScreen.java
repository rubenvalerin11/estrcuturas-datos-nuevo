package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;
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
    private PlayerController playerController;
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

        // Crear jugador
        player = new Player(100, 80f);
        playerController = new PlayerController(player);

        // Crear controller general
        controller = new GameController(player);
        controller.onLevelChanged();
    }

    @Override
    public void render(float delta) {

        // PAUSA
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }

        // ========================
        // INPUT DEL JUGADOR
        // ========================
        playerController.handleInput(delta);

        // ========================
        // UPDATE GENERAL
        // ========================
        player.update(delta);
        controller.update(delta);

        LevelManager lm = controller.getLevelManager();

        // ===========================================
        // PASO AUTOMÁTICO NIVEL 1 → NIVEL 2 (caminar)
        // ===========================================
        if (lm.getLevel() == 1 && player.getX() > Player.WORLD_WIDTH - 150) {

            lm.gotoLevel2FromWalk();
            controller.onLevelChanged();

            player.unlockMelee(); // ahora puede atacar
            player.setPosition(100, 80f); // reiniciar posición del nivel
        }

        // ===========================================
        // PASAR DE NIVEL 2 → 3 (kills)
        // ===========================================
        if (lm.getLevel() == 2 && lm.shouldAdvanceLevel()) {

            lm.advanceLevel();
            controller.onLevelChanged();
            player.setPosition(100, 80f);
        }

        // ===========================================
        // PASAR DE NIVEL 3 → 4 (minotauro muerto)
        // ===========================================
        if (lm.getLevel() == 3 && lm.shouldAdvanceLevel()) {

            lm.advanceLevel();
            controller.onLevelChanged();
            player.setPosition(100, 80f);
        }

        // ===========================================
        // VICTORIA
        // ===========================================
        if (controller.isBossDefeated()) {
            StatsManager.get().partidaTerminada(true, lm.getKills());
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // ===========================================
        // GAME OVER
        // ===========================================
        if (!player.isAlive()) {
            StatsManager.get().partidaTerminada(false, lm.getKills());
            game.setScreen(new GameOverScreen(game));
            return;
        }

        // ========================
        // DIBUJADO
        // ========================
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        // Render jugador + enemigos
        player.render(batch);
        controller.render(batch);

        // ========================
        // BARRA DE VIDA
        // ========================
        float p = player.getHealthPercent();
        batch.draw(healthBar, 20, 580, healthBar.getWidth() * p, healthBar.getHeight());

        // =========================
        // HUD: NIVEL + KILLS
        // =========================
        font.draw(batch, "Nivel: " + lm.getLevel(), 800, 620);
        font.draw(batch, "Kills: " + lm.getKills() + "/" + lm.getRequiredKills(), 750, 590);

        // HUD de Arma
        font.draw(batch, "Arma: " + player.getWeaponName(), 20, 550);

        // =========================
        // TEXTO TUTORIAL NIVEL 1
        // =========================
        if (lm.getLevel() == 1) {
            font.draw(batch, "NIVEL 1 - INTRO", 350, 350);
            font.draw(batch, "Camina hacia la derecha →", 350, 320);
        }

        // =========================
        // BARRA DEL JEFE (nivel 4)
        // =========================
        if (lm.getLevel() == 4) {
            float bossP = controller.getBossHealthPercent();
            batch.draw(bossHealthBar, 300, 650, bossHealthBar.getWidth() * bossP, bossHealthBar.getHeight());
            font.draw(batch, "DRACULA", 380, 630);
        }

        batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        healthBar.dispose();
        bossHealthBar.dispose();
        font.dispose();
        controller.dispose();
    }
}
