package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {

    private float x, y;
    private float speed = 180f;
    private int vida = 100;
    private boolean vivo = true;

    // Animaciones
    private Texture walkSheet;
    private Animation<TextureRegion> walkAnim;
    private float stateTime = 0f;

    // Tamaños reales del sprite
    private float width;
    private float height;

    // Ataque simple (placeholder)
    private boolean attacking = false;
    private float attackTimer = 0f;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        walkSheet = new Texture("alucarcaminando.png");

        int FRAME_COLS = 14;
        int frameWidth = walkSheet.getWidth() / FRAME_COLS;
        int frameHeight = walkSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];

        for (int i = 0; i < FRAME_COLS; i++) {
            frames[i] = tmp[0][i];
        }

        walkAnim = new Animation<>(0.07f, frames);
        walkAnim.setPlayMode(Animation.PlayMode.LOOP);

        width = frameWidth * 1.4f;
        height = frameHeight * 1.4f;
    }

    public void update(float delta) {
        if (!vivo) return;

        stateTime += delta;

        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy = -1;

        float len = (float) Math.sqrt(dx*dx + dy*dy);
        if (len != 0) {
            dx /= len;
            dy /= len;
        }

        x += dx * speed * delta;
        y += dy * speed * delta;

        // Ataque con J (placeholder)
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) {
            attacking = true;
            attackTimer = 0.2f;
        }

        if (attacking) {
            attackTimer -= delta;
            if (attackTimer <= 0) attacking = false;
        }

        if (vida <= 0) vivo = false;
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = walkAnim.getKeyFrame(stateTime);
        batch.draw(frame, x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 15, y + 10, width - 30, height - 15);
    }

    public Rectangle getAttackBounds() {
        if (!attacking) return new Rectangle(0,0,0,0);

        return new Rectangle(x + width, y + height * 0.25f, 40, height * 0.5f);
    }

    public void receiveDamage(int d) {
        vida -= d;
        if (vida <= 0) {
            vivo = false;
        }
    }

    public boolean isAlive() {
        return vivo;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void reset() {
        vida = 100;
        vivo = true;
        x = 100;
        y = 100;
    }
}
