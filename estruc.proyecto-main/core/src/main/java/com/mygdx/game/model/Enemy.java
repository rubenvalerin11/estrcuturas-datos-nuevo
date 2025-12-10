package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.player.PlayerAnimLoader;

public class Enemy {

    protected float x, y;
    protected int vida = 40;
    protected int vidaMax = 40;
    protected int damage = 10;

    protected Animation<TextureRegion> walkAnim;
    protected float stateTime = 0;

    public Enemy(float x, float y, String sprite, int frames) {
        this.x = x;
        this.y = y;
        walkAnim = PlayerAnimLoader.loadHorizontalSheet(sprite, frames, 0.15f);
    }

    public void update(float delta) {
        stateTime += delta;
        x -= 40 * delta;
    }

    public TextureRegion getFrame() {
        return walkAnim.getKeyFrame(stateTime, true);
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public boolean estaMuerto() { return vida <= 0; }

    public void recibirDano(int dmg) {
        vida -= dmg;
    }

    public int getDamage() {
        return damage;
    }
}
