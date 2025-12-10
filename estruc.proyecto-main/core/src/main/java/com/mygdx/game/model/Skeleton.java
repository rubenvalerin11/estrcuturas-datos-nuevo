package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Skeleton extends Enemy {

    private Texture walkSheet;
    private Texture deathSheet;

    private Animation<TextureRegion> walkAnim;
    private Animation<TextureRegion> deathAnim;

    private TextureRegion currentFrame;

    private float animTimer = 0f;
    private boolean dying = false;

    public Skeleton(float x, float y) {
        super(x, y, 60f, 40, 8);

        // =======================
        // CARGA DE SPRITES
        // =======================
        walkSheet = new Texture("eskeletocaminando.png");
        deathSheet = new Texture("eskeletomuriendo.png");

        walkAnim = createAnimation(walkSheet, 4, 0.12f);
        deathAnim = createAnimation(deathSheet, 6, 0.10f);

        currentFrame = walkAnim.getKeyFrame(0);
        width = currentFrame.getRegionWidth() * 1.4f;
        height = currentFrame.getRegionHeight() * 1.4f;
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int frameCount, float speed) {
        int frameW = sheet.getWidth() / frameCount;
        int frameH = sheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameW, frameH);
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) frames[i] = tmp[0][i];

        return new Animation<>(speed, frames);
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        animTimer += delta;

        if (!vivo) {
            dying = true;
            currentFrame = deathAnim.getKeyFrame(animTimer, false);
            return;
        }

        // Movimiento simple hacia el jugador
        float dx = (playerX > x) ? 1 : -1;
        x += dx * speed * delta;

        currentFrame = walkAnim.getKeyFrame(animTimer, true);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y, width, height);
    }

    @Override
    public void dispose() {
        walkSheet.dispose();
        deathSheet.dispose();
    }
}
