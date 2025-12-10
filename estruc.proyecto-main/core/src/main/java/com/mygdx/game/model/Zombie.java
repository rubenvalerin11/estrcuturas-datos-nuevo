package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Zombie extends Enemy {

    private Texture walkSheet;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime = 0f;

    public Zombie(float x, float y) {
        // más vida y daño que el esqueleto
        super(x, y, 90f, 70, 15);

        // usamos el minotauro
        walkSheet = new Texture("minotaurocaminando.png");

        int FRAME_COLS = 6; // AJUSTA según frames
        int frameWidth = walkSheet.getWidth() / FRAME_COLS;
        int frameHeight = walkSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            frames[i] = tmp[0][i];
        }

        walkAnimation = new Animation<>(0.1f, frames);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        width = frameWidth;
        height = frameHeight;
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        if (!vivo) return;

        stateTime += delta;

        float dx = playerX - x;
        float dy = playerY - y;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
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
        TextureRegion frame = walkAnimation.getKeyFrame(stateTime);
        batch.draw(frame, x, y);
    }

    @Override
    public void dispose() {
        walkSheet.dispose();
    }
}
