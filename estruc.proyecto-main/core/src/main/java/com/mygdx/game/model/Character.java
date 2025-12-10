package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class Character {
    protected float x, y;
    protected Texture texture;
    protected int vida;

    public Character(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.vida = 100;
    }

    // Si Character ya existe pero es diferente, ajusta el constructor
    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
}
