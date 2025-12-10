package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.estructuras.StatsManager;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerController;

public class GameScreen implements Screen {

    private final Game game;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture bgLevel1;
    private Texture bgLevel2;
    private Texture bgLevel3;
    private Texture bgLevel4;

    private Texture healthBar;
    private Texture bossHealthBar;

    private BitmapFont font;

    private final Player player;
    private final PlayerController playerController;
    private final GameController controller;

    public GameScreen(Game game) {
        this(game, 1);
    }

    public GameScreen(Game game, int startLevel) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        batch = new SpriteBatch();

        bgLevel1 = new Texture("Level1_bg.png");
        bgLevel2 = new Texture("castle_entrance.png");
        bgLevel3 = new Texture("castle_inside.png");
        bgLevel4 = new Texture("altar_dracula.png");

        healthBar = new Texture("health_bar.png");
        bossHealthBar = new Texture("boss_health.png");

        font = new BitmapFont();
        font.getData().setScale(1.5f);

        player = new Player(100, 80f);
        playerController = new PlayerController(player);
        controller = new GameController(player, startLevel);

        if (startLevel >= 2) {
            player.unlockMelee();
        }
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new PauseScreen(game, this));
            return;
        }

        playerController.update(delta);
        player.update(delta);
        controller.update(delta);

        LevelManager lm = controller.getLevelManager();
        int lvl = lm.getLevel();

        // -------- LÓGICA DE NIVELES --------

        // Nivel 1: solo caminar
        if (lvl == 1 && player.getX() > Player.WORLD_WIDTH - 150f) {
            lm.gotoLevel2FromWalk();
            controller.onLevelChanged();
            player.unlockMelee();
            player.setPosition(100, 80f);
            player.resetAnim();
        }

        // Nivel 2 -> 3 (8 esqueletos)
        if (lvl == 2 && lm.shouldAdvanceLevel()) {
            lm.advanceLevel();
            controller.onLevelChanged();
            player.setPosition(100, 80f);
            player.resetAnim();
        }

        // Nivel 3 -> 4 (minotauro muerto)
        if (lvl == 3 && lm.shouldAdvanceLevel()) {
            lm.advanceLevel();
            controller.onLevelChanged();
            player.setPosition(100, 80f);
            player.resetAnim();
        }

        // Victoria vs Drácula
        if (controller.isBossDefeated()) {
            StatsManager.get().partidaTerminada(true, lm.getKills());
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // Game Over
        if (!player.isAlive()) {
            StatsManager.get().partidaTerminada(false, lm.getKills());
            game.setScreen(new GameOverScreen(game));
            return;
        }

        // -------- DIBUJO --------

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        Texture bg;
        switch (lvl) {
            case 1: default: bg = bgLevel1; break;
            case 2: bg = bgLevel2; break;
            case 3: bg = bgLevel3; break;
            case 4: bg = bgLevel4; break;
        }

        batch.draw(bg, 0, 0, Player.WORLD_WIDTH, Player.WORLD_HEIGHT);

        player.render(batch);
        controller.render(batch);

        float hp = player.getHealthPercent();
        batch.draw(healthBar, 20, 650,
            healthBar.getWidth() * hp,
            healthBar.getHeight());

        font.draw(batch, "Nivel: " + lvl, 900, 700);

        if (lvl == 2 || lvl == 3) {
            font.draw(batch,
                "Kills: " + lm.getKills() + "/" + lm.getRequiredKills(),
                880, 670);
        }

        font.draw(batch, "Arma: " + player.getWeaponName(), 20, 620);

        if (lvl == 1) {
            font.draw(batch, "NIVEL 1 - TUTORIAL", 430, 400);
            font.draw(batch, "Moverse: A / D", 430, 370);
            font.draw(batch, "Atacar: ESPACIO (desbloqueado en nivel 2)", 430, 340);
            font.draw(batch, "Cambiar arma: 1 / 2 / 3", 430, 310);
            font.draw(batch, "Camina hacia la derecha para comenzar", 430, 280);
        }

        if (lvl == 4) {
            float bossPercent = controller.getBossHealthPercent();
            if (bossPercent > 0) {
                batch.draw(bossHealthBar, 300, 680,
                    bossHealthBar.getWidth() * bossPercent,
                    bossHealthBar.getHeight());
                font.draw(batch, "DRACULA", 500, 660);
            }
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
        bgLevel1.dispose();
        bgLevel2.dispose();
        bgLevel3.dispose();
        bgLevel4.dispose();
        healthBar.dispose();
        bossHealthBar.dispose();
        font.dispose();
        controller.dispose();
        player.dispose();
    }
}
