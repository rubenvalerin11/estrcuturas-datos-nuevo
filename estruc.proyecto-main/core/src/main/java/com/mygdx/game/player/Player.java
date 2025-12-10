package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    public static final float WORLD_WIDTH = 1280;
    public static final float WORLD_HEIGHT = 720;

    private float x, y;
    private float speed = 220f;

    private int maxHealth = 100;
    private int health = 100;

    private boolean alive = true;

    private Texture walkSheet;
    private TextureRegion[] walkRight;
    private TextureRegion[] walkLeft;
    private TextureRegion currentFrame;
    private float animTimer = 0f;
    private int frameCount = 14;

    private boolean facingRight = true;
    private boolean moving = false;

    public enum WeaponType {
        NONE, MELEE, ESPADA1, ESPADA2
    }

    private WeaponType weapon = WeaponType.NONE;
    private boolean meleeUnlocked = false;
    private boolean espada1Unlocked = false;
    private boolean espada2Unlocked = false;

    private boolean attacking = false;
    private float attackTimer = 0f;
    private final Rectangle attackBounds = new Rectangle(0, 0, 0, 0);

    public Player(float x, float y) {
        this.x = x;
        this.y = y;

        walkSheet = new Texture("alucarcaminando.png");
        int frameW = walkSheet.getWidth() / frameCount;
        int frameH = walkSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, frameW, frameH);
        walkRight = new TextureRegion[frameCount];
        walkLeft = new TextureRegion[frameCount];

        for (int i = 0; i < frameCount; i++) {
            walkRight[i] = tmp[0][i];
            walkLeft[i] = new TextureRegion(tmp[0][i]);
            walkLeft[i].flip(true, false);
        }

        currentFrame = walkRight[0];
    }

    // -------- GETTERS BÁSICOS --------

    public float getX() { return x; }
    public float getY() { return y; }
    public boolean isAlive() { return alive; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public Rectangle getAttackBounds() {
        return attackBounds;
    }

    public float getHealthPercent() {
        return (float) health / maxHealth;
    }

    public String getWeaponName() {
        switch (weapon) {
            case MELEE: return "Melee";
            case ESPADA1: return "Espada I";
            case ESPADA2: return "Espada II";
            default: return "NONE";
        }
    }

    public int getAttackDamage() {
        switch (weapon) {
            case MELEE:   return 10;
            case ESPADA1: return 18;
            case ESPADA2: return 25;
            default:      return 0;
        }
    }

    // -------- VIDA / RESET --------

    public void receiveDamage(int amount) {
        if (!alive) return;
        health -= amount;
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
        weapon = meleeUnlocked ? WeaponType.MELEE : WeaponType.NONE;
        attackBounds.set(0, 0, 0, 0);
        animTimer = 0f;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // -------- ARMAS --------

    public void unlockMelee() {
        meleeUnlocked = true;
        if (weapon == WeaponType.NONE) {
            weapon = WeaponType.MELEE;
        }
    }

    public void unlockEspada1() {
        espada1Unlocked = true;
    }

    public void unlockEspada2() {
        espada2Unlocked = true;
    }

    public void setWeapon(WeaponType w) {
        switch (w) {
            case MELEE:
                if (meleeUnlocked) weapon = w;
                break;
            case ESPADA1:
                if (espada1Unlocked) weapon = w;
                break;
            case ESPADA2:
                if (espada2Unlocked) weapon = w;
                break;
            default:
                weapon = WeaponType.NONE;
        }
    }

    // -------- MOVIMIENTO Y ANIMACIÓN --------

    public void move(float dx) {
        if (!alive) return;

        moving = dx != 0;

        if (dx > 0) facingRight = true;
        if (dx < 0) facingRight = false;

        x += dx;

        if (x < 0) x = 0;
        if (x > WORLD_WIDTH - currentFrame.getRegionWidth()) {
            x = WORLD_WIDTH - currentFrame.getRegionWidth();
        }
    }

    public void attack() {
        if (!alive) return;
        if (weapon == WeaponType.NONE) return;

        attacking = true;
        attackTimer = 0.18f;
        updateAttackBounds();
    }

    private void updateAttackBounds() {
        if (!attacking) {
            attackBounds.set(0, 0, 0, 0);
            return;
        }

        float width;
        float height;

        switch (weapon) {
            case MELEE:
                width = 35;
                height = 25;
                break;
            case ESPADA1:
                width = 55;
                height = 30;
                break;
            case ESPADA2:
                width = 70;
                height = 35;
                break;
            default:
                width = 0;
                height = 0;
        }

        if (width == 0) {
            attackBounds.set(0, 0, 0, 0);
            return;
        }

        if (facingRight) {
            attackBounds.set(x + currentFrame.getRegionWidth(), y + 15, width, height);
        } else {
            attackBounds.set(x - width, y + 15, width, height);
        }
    }

    public void resetAnim() {
        animTimer = 0;
    }

    public void update(float delta) {
        if (!alive) return;

        animTimer += delta * (moving ? 10f : 4f);
        int index = (int) (animTimer % frameCount);
        currentFrame = facingRight ? walkRight[index] : walkLeft[index];

        if (attacking) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                attacking = false;
                attackBounds.set(0, 0, 0, 0);
            } else {
                updateAttackBounds();
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y);
    }

    public void dispose() {
        walkSheet.dispose();
    }
}
