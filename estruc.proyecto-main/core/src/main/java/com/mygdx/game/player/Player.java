package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private float x, y;
    private float speed = 200f;

    private Texture[] walkFrames;
    private float animationTimer;
    private int frameIndex;
    private float frameDuration = 0.1f;
    private boolean facingRight = true;

    // Salud
    private int maxHealth = 100;
    private int health = 100;
    private boolean alive = true;

    // Ataque cuerpo a cuerpo
    private boolean attacking = false;
    private float attackTimer = 0f;
    private float attackDuration = 0.2f; // segundos
    private int attackDamage = 15;

    // Colisiones
    private float width;
    private float height;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        loadWalkFrames();
    }

    private void loadWalkFrames() {
        // Usa los 6 frames de alucard caminando
        walkFrames = new Texture[6];
        for (int i = 0; i < 6; i++) {
            walkFrames[i] = new Texture("alucard_walk_" + i + ".png");
        }
        width = walkFrames[0].getWidth();
        height = walkFrames[0].getHeight();
    }

    public void update(float delta, float moveX, float moveY, boolean attackPressed) {
        // Movimiento
        if (moveX != 0 || moveY != 0) {
            x += moveX * speed * delta;
            y += moveY * speed * delta;
            if (moveX > 0) facingRight = true;
            if (moveX < 0) facingRight = false;

            // Animación caminar
            animationTimer += delta;
            if (animationTimer >= frameDuration) {
                animationTimer = 0f;
                frameIndex = (frameIndex + 1) % walkFrames.length;
            }
        } else {
            // Quieto → primer frame
            frameIndex = 0;
            animationTimer = 0f;
        }

        // Ataque
        if (attackPressed && !attacking) {
            attacking = true;
            attackTimer = 0f;
        }

        if (attacking) {
            attackTimer += delta;
            if (attackTimer >= attackDuration) {
                attacking = false;
            }
        }
    }

    public void render(SpriteBatch batch) {
        Texture frame = walkFrames[frameIndex];
        if (facingRight) {
            batch.draw(frame, x, y);
        } else {
            batch.draw(frame, x + width, y, -width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getAttackBounds() {
        if (!attacking) {
            return new Rectangle(0, 0, 0, 0);
        }
        float attackWidth = width * 0.6f;
        float attackHeight = height * 0.6f;
        float attackX = facingRight ? x + width : x - attackWidth;
        float attackY = y + height * 0.2f;
        return new Rectangle(attackX, attackY, attackWidth, attackHeight);
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

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getHealthPercent() {
        return (float) health / (float) maxHealth;
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public void dispose() {
        if (walkFrames != null) {
            for (Texture t : walkFrames) {
                if (t != null) t.dispose();
            }
        }
    }
    public void reset() {
        this.health = maxHealth;
        this.alive = true;
        this.x = 200;
        this.y = 150;
    }

}
