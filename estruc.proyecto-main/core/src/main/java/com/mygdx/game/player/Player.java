package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private float x, y;
    private Texture texture;
    private int vida;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.vida = 100;
    }

    // MÉTODOS FALTANTES para CollisionController:
    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 80); // Valores aproximados
    }

    public Rectangle getAttackBounds() {
        return new Rectangle(x + 50, y, 60, 30); // Valores aproximados
    }

    public void receiveDamage(int damage) {
        this.vida -= damage;
        if (vida < 0) vida = 0;
    }

    public String getWeaponName() {
        return "Espada"; // Temporal
    }

    // Métodos existentes...
    public float getX() { return x; }
    public float getY() { return y; }
    public void setPosition(float x, float y) { this.x = x; this.y = y; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    public void update(float delta) {
        // Lógica simple
    }

    public void render(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    public void dispose() {
        if (texture != null) texture.dispose();
    }
}
