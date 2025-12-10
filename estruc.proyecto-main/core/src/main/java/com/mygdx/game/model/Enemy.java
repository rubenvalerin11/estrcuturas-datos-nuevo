package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Enemy {

    protected float x, y;
    protected float speed;

    protected int vida;
    protected int vidaMaxima;
    protected int dano;

    protected boolean vivo = true;

    protected float width = 64;
    protected float height = 64;

    public Enemy(float x, float y, float speed, int vida, int dano) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.vida = vida;
        this.vidaMaxima = vida;   // << NUEVO: vida máxima igual a vida inicial
        this.dano = dano;
    }

    public abstract void update(float delta, float playerX, float playerY);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    public void recibirDano(int d) {
        vida -= d;
        if (vida <= 0) {
            vida = 0;
            vivo = false;
        }
    }

    public boolean estaVivo() {
        return vivo;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getDano() {
        return dano;
    }

    // << NUEVOS GETTERS PARA GameScreen >>
    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public float getX() { return x; }
    public float getY() { return y; }
}
