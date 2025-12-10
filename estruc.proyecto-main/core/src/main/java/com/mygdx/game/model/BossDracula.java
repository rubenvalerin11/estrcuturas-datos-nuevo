package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BossDracula extends Enemy {

    private Texture idleTexture;
    private Texture attackSheet;
    private Animation<TextureRegion> attackAnimation;
    private float stateTime = 0f;
    private boolean attacking = false;

    public BossDracula(float x, float y) {
        // Vida 5× esqueleto (40*5 = 200), daño 5× (10*5 = 50)
        super(x, y, 110f, 200, 50);

        idleTexture = new Texture("draculadepie.png");
        attackSheet = new Texture("ataquedracula.png");

        int FRAME_COLS = 6; // AJUSTA al número real de frames del ataque
        int frameWidth = attackSheet.getWidth() / FRAME_COLS;
        int frameHeight = attackSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(attackSheet, frameWidth, frameHeight);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        for (int i = 0; i < FRAME_COLS; i++) {
            frames[i] = tmp[0][i];
        }

        attackAnimation = new Animation<>(0.08f, frames);
        attackAnimation.setPlayMode(Animation.PlayMode.LOOP);

        width = idleTexture.getWidth();
        height = idleTexture.getHeight();
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        if (!vivo) return;

        stateTime += delta;

        // movimiento simple hacia el jugador
        float dx = playerX - x;
        float dy = playerY - y;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        if (len > 1f) {
            dx /= len;
            dy /= len;
            x += dx * speed * delta;
            y += dy * speed * delta;
        }

        // ataque “activo” cuando está cerca (solo visual)
        attacking = len < 120f;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!vivo) return;

        if (attacking) {
            TextureRegion frame = attackAnimation.getKeyFrame(stateTime);
            batch.draw(frame, x, y);
        } else {
            batch.draw(idleTexture, x, y);
        }
    }

    @Override
    public void dispose() {
        idleTexture.dispose();
        attackSheet.dispose();
    }
}
