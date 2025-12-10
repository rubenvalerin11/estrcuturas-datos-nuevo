package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BossDracula extends Enemy {

    private Texture sprite;

    public BossDracula(float x, float y) {
        super(x, y, 20f, 300, 20); // speed, vida, daño
        sprite = new Texture("skeleton_0.png");  // cámbialo por tu sprite real
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
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
        if (vivo) batch.draw(sprite, x, y);
    }

    @Override
    public void dispose() {
        if (sprite != null) sprite.dispose();
    }
}
