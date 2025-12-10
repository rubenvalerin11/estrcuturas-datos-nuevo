package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Zombie extends Enemy {

    private Texture[] walkFrames;
    private float animTimer = 0f;
    private float frameDuration = 0.2f;
    private int frameIndex = 0;
    private boolean mirandoDerecha = true;

    public Zombie(float x, float y) {
        super(x, y, 60f, 40, 5);
        walkFrames = new Texture[2];
        walkFrames[0] = new Texture("zombie_walk_0.png");
        walkFrames[1] = new Texture("zombie_walk_1.png");
        width = walkFrames[0].getWidth();
        height = walkFrames[0].getHeight();
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
            mirandoDerecha = dx >= 0;
        }

        animTimer += delta;
        if (animTimer >= frameDuration) {
            animTimer = 0f;
            frameIndex = (frameIndex + 1) % walkFrames.length;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!vivo) return;
        Texture frame = walkFrames[frameIndex];
        if (mirandoDerecha) {
            batch.draw(frame, x, y);
        } else {
            batch.draw(frame, x + width, y, -width, height);
        }
    }

    @Override
    public void dispose() {
        for (Texture t : walkFrames) {
            if (t != null) t.dispose();
        }
    }
}
