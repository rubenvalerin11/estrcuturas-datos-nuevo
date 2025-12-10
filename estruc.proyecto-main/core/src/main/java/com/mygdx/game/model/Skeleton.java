package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Skeleton extends Enemy {

    private Texture sprite;

    public Skeleton(float x, float y) {
        super(x, y, 40f, 30, 8);
        sprite = new Texture("skeleton_0.png");
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        // Enemigo más lento, movimiento sencillo hacia el jugador
        if (!vivo) return;
        float dx = playerX - x;
        float dy = playerY - y;
        float len = (float)Math.sqrt(dx * dx + dy * dy);
        if (len > 1f) {
            dx /= len;
            dy /= len;
            x += dx * speed * delta;
            y += dy * speed * delta;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!vivo) return;
        batch.draw(sprite, x, y);
    }

    @Override
    public void dispose() {
        if (sprite != null) sprite.dispose();
    }
}
