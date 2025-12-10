package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minotauro extends Enemy {

    private Texture walkSheet;
    private Texture deathSheet;

    private Animation<TextureRegion> walkAnim;
    private Animation<TextureRegion> deathAnim;

    private TextureRegion currentFrame;

    private float animTimer = 0f;
    private boolean dying = false;

    public Minotauro(float x, float y) {
        super(x, y, 70f, 80, 15);

        walkSheet = new Texture("minotaurocaminando.png");
        deathSheet = new Texture("minotauromuriendo.png");

        walkAnim = createAnimation(walkSheet, 10, 0.10f);
        deathAnim = createAnimation(deathSheet, 3, 0.12f);

        currentFrame = walkAnim.getKeyFrame(0);
        width = currentFrame.getRegionWidth() * 1.6f;
        height = currentFrame.getRegionHeight() * 1.6f;
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int framesCount, float speed) {
        int frameW = sheet.getWidth() / framesCount;
        int frameH = sheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(sheet, frameW, frameH);
        TextureRegion[] frames = new TextureRegion[framesCount];
        for (int i = 0; i < framesCount; i++) frames[i] = tmp[0][i];

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
