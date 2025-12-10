package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.LevelManager;
import com.mygdx.game.model.Enemy;
import com.mygdx.game.player.Player;

public class GameScreen implements Screen {

    private final MyGame game;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Texture background;
    private Texture healthBar;
    private Texture bossHealthBar;

    private BitmapFont font;

    private Player player;
    private GameController controller;

    public GameScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 900, 600);

        batch = new SpriteBatch();

        background = new Texture("Level1_bg.png");
        healthBar = new Texture("health_bar.png");
        bossHealthBar = new Texture("boss_health.png");

        font = new BitmapFont();

        player = new Player(200, 150);
        controller = new GameController(player);
    }

    @Override
    public void render(float delta) {

        // si está muerto → reiniciar juego
        if (!player.isAlive()) {
            controller.resetGame();
            game.setScreen(new GameScreen(game));
            return;
        }

        LevelManager lm = controller.getLevelManager();

        // PASAR DE NIVEL AUTOMÁTICAMENTE
        if (lm.shouldAdvanceLevel()) {
            lm.advanceLevel();
        }

        // SI ES NIVEL DE JEFE Y YA NO HAY ENEMIGOS → GANASTE
        if (controller.isBossDefeated()) {
            game.setScreen(new VictoryScreen(game));
            return;
        }

        // Entrada
        float moveX = 0f, moveY = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveX = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveY = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveY = -1;
        boolean attack = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);

        player.update(delta, moveX, moveY, attack);
        controller.update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0);

        // jugador y enemigos
        player.render(batch);
        controller.render(batch);

        // barra de vida
        float p = player.getHealthPercent();
        batch.draw(healthBar, 20, 560, healthBar.getWidth() * p, healthBar.getHeight());

        // texto nivel
        font.draw(batch, "Nivel: " + lm.getLevel(), 750, 580);
        font.draw(batch, "Kills: " + lm.getKills() + "/10", 750, 550);

        // barra de vida del jefe (si aplica)
        if (controller.getLevelManager().isBossLevel()) {
            Enemy boss = controller.getBoss();
            if (boss != null) {
                float pBoss = (float) boss.getVida() / (float) boss.getVidaMaxima();
                batch.draw(bossHealthBar, 200, 560, 500 * pBoss, 20);
            }
        }

        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        healthBar.dispose();
        bossHealthBar.dispose();
        player.dispose();
        controller.dispose();
    }
}
