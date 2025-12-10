package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public enum WeaponType { NONE, MELEE, ESPADA1, ESPADA2 }

    public static final float WORLD_WIDTH = 1280;
    public static final float WORLD_HEIGHT = 720;

    private float x, y;
    private float speed = 200;
    private int maxHealth = 100;
    private int health = 100;
    private boolean alive = true;

    private boolean meleeUnlocked = false;
    private WeaponType currentWeapon = WeaponType.NONE;

    // Animación
    private Texture walkSheet;
    private TextureRegion[] frames;
    private int frameCount = 14;
    private float animTimer = 0;
    private TextureRegion currentFrame;

    // Ataque
    private boolean attacking = false;
    private float attackTimer = 0f;
    private Rectangle attackBounds = new Rectangle(0,0,0,0);

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        walkSheet = new Texture("alucarcaminando.png");

        int frameW = walkSheet.getWidth() / frameCount;
        int frameH = walkSheet.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, frameW, frameH);

        frames = new TextureRegion[frameCount];
        for(int i=0;i<frameCount;i++)
            frames[i] = tmp[0][i];

        currentFrame = frames[0];
    }

    // ---------------- GETTERS ----------------
    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle getBounds() { return new Rectangle(x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight()); }
    public Rectangle getAttackBounds() { return attackBounds; }
    public float getHealthPercent() { return (float)health / maxHealth; }
    public boolean isAlive() { return alive; }
    public String getWeaponName() { return currentWeapon.toString(); }

    // ---------------- MOVIMIENTO ----------------
    public void move(float dx) {
        x += dx;

        if (x < 0) x = 0;
        if (x > WORLD_WIDTH - currentFrame.getRegionWidth())
            x = WORLD_WIDTH - currentFrame.getRegionWidth();
    }

    public void setPosition(float px, float py) {
        this.x = px;
        this.y = py;
    }

    public void resetAnim() {
        animTimer = 0;
        currentFrame = frames[0];
    }

    // ---------------- ARMAS ----------------
    public void unlockMelee() { meleeUnlocked = true; }

    public void setWeapon(WeaponType type) {
        this.currentWeapon = type;
    }

    // ---------------- ATAQUE ----------------
    public void attack() {
        if (!meleeUnlocked) return;

        attacking = true;
        attackTimer = 0.2f; // ventana pequeña
    }

    // ---------------- UPDATE ----------------
    public void update(float delta) {
        if (!alive) return;

        animTimer += delta * 10;
        currentFrame = frames[(int)(animTimer % frameCount)];

        if (attacking) {
            attackTimer -= delta;

            attackBounds.set(x + currentFrame.getRegionWidth(), y + 20, 40, 20);

            if (attackTimer <= 0) {
                attacking = false;
                attackBounds.set(0,0,0,0);
            }
        }
    }

    // ---------------- DAÑO ----------------
    public void receiveDamage(int dmg) {
        if (!alive) return;
        health -= dmg;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public void reset() {
        health = maxHealth;
        alive = true;
        x = 100;
        y = 80;
        currentWeapon = WeaponType.NONE;
        meleeUnlocked = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y);
    }

    public void dispose() {
        walkSheet.dispose();
    }
}
