package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private float x, y;
    private float speed = 200f;

    private int maxHealth = 100;
    private int health = 100;
    private boolean alive = true;

    private boolean facingRight = true;

    // animación caminar
    private Texture walkSheet;
    private Animation<TextureRegion> walkAnimation;
    private TextureRegion idleFrame;
    private float animTime = 0f;

    // ataque
    private boolean attacking = false;
    private float attackTimer = 0f;
    private float attackDuration = 0.2f;
    private int attackDamage = 20;

    // hitboxes
    private float width;
    private float height;

    private Rectangle bodyBounds = new Rectangle();
    private Rectangle attackBounds = new Rectangle();

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        // sprites:
        //  - alucardcaminando.png  (spritesheet horizontal)
        //  - alucarddepie.png      (frame quieto)
        walkSheet = new Texture("alucardcaminando.png");
        idleFrame = new TextureRegion(new Texture("alucarddepie.png"));

        // 👉 AJUSTA ESTE NÚMERO AL NÚMERO DE FRAMES DE TU SPRITESHEET
        int FRAME_COLS = 6; // si tu sheet tiene 8 frames, cambia a 8

        int frameWidth = walkSheet.getWidth() / FRAME_COLS;
        int frameHeight = walkSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            frames[i] = tmp[0][i];
        }

        walkAnimation = new Animation<>(0.08f, frames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        width = frameWidth;
        height = frameHeight;
    }

    public void update(float delta, float moveX, float moveY, boolean attackPressed) {
        if (!alive) return;

        // movimiento
        float velX = moveX * speed;
        float velY = moveY * speed;

        if (velX != 0 || velY != 0) {
            animTime += delta;
        } else {
            animTime = 0; // vuelve a primer frame cuando está quieto
        }

        x += velX * delta;
        y += velY * delta;

        if (velX > 0) facingRight = true;
        if (velX < 0) facingRight = false;

        // ataque
        if (attackPressed && !attacking) {
            attacking = true;
            attackTimer = 0f;
        }

        if (attacking) {
            attackTimer += delta;
            if (attackTimer >= attackDuration) {
                attacking = false;
                attackTimer = 0f;
            }
        }

        // actualizar hitboxes
        bodyBounds.set(x + width * 0.25f, y, width * 0.5f, height * 0.9f);

        if (attacking) {
            float attackWidth = 40f;
            float attackHeight = height * 0.6f;
            float attackX = facingRight ? (bodyBounds.x + bodyBounds.width) : (bodyBounds.x - attackWidth);
            float attackY = bodyBounds.y + bodyBounds.height * 0.2f;
            attackBounds.set(attackX, attackY, attackWidth, attackHeight);
        } else {
            attackBounds.set(0, 0, 0, 0);
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion current;

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A) ||
            Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D) ||
            Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W) ||
            Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            current = walkAnimation.getKeyFrame(animTime);
        } else {
            current = idleFrame;
        }

        float drawX = x;
        float drawY = y;

        if (facingRight) {
            batch.draw(current, drawX, drawY);
        } else {
            batch.draw(current, drawX + width, drawY, -width, height);
        }

        // (Si quieres depurar hitboxes, aquí puedes dibujar rectángulos con ShapeRenderer)
    }

    public void dispose() {
        walkSheet.dispose();
        idleFrame.getTexture().dispose();
    }

    public Rectangle getBounds() {
        return bodyBounds;
    }

    public Rectangle getAttackBounds() {
        return attackBounds;
    }

    public void receiveDamage(int dmg) {
        if (!alive) return;
        health -= dmg;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public float getHealthPercent() {
        return (float) health / (float) maxHealth;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void reset() {
        this.health = maxHealth;
        this.alive = true;
        this.x = 200;
        this.y = 150;
    }
}
