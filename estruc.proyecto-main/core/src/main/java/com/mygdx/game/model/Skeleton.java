package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Skeleton extends Enemy {

    private Texture walkSheet, deathSheet;
    private Animation<TextureRegion> walkAnim, deathAnim;
    private float stateTime = 0f;
    private boolean dying = false;

    public Skeleton(float x, float y) {
        super(x, y, 60f, 40, 8);

        walkSheet = new Texture("eskeletocaminando.png");
        deathSheet = new Texture("esqueletomuriendo.png");

        int FW = walkSheet.getWidth() / 5;
        int FH = walkSheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, FW, FH);
        TextureRegion[] frames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) frames[i] = tmp[0][i];

        walkAnim = new Animation<TextureRegion>(0.12f, frames);
        walkAnim.setPlayMode(Animation.PlayMode.LOOP);

        // muerte
        TextureRegion[][] dt = TextureRegion.split(deathSheet, FW, FH);
        TextureRegion[] df = new TextureRegion[5];
        for (int i = 0; i < 5; i++) df[i] = dt[0][i];

        deathAnim = new Animation<TextureRegion>(0.10f, df);
        deathAnim.setPlayMode(Animation.PlayMode.NORMAL);

        width = FW * 1.4f;
        height = FH * 1.4f;
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        stateTime += delta;

        if (!vivo) {
            dying = true;
            return;
        }

        float dx = playerX - x;
        float dy = playerY - y;
        float len = (float) Math.sqrt(dx * dx + dy * dy);

        if (len > 1) {
            dx /= len;
            dy /= len;
            x += dx * speed * delta;
            y += dy * speed * delta;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame =
            dying ? deathAnim.getKeyFrame(stateTime) : walkAnim.getKeyFrame(stateTime);

        batch.draw(frame, x, y, width, height);
    }

    @Override
    public void dispose() {
        walkSheet.dispose();
        deathSheet.dispose();
    }
}
