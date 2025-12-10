package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Minotauro extends Enemy {

    private Texture sheet;
    private Animation<TextureRegion> anim;
    private float time = 0f;

    public Minotauro(float x, float y) {
        super(x, y, 70f, 80, 15);

        sheet = new Texture("minotaurocaminando.png");

        int COLS = 10;
        int fw = sheet.getWidth() / COLS;
        int fh = sheet.getHeight();

        TextureRegion[] frames = new TextureRegion[COLS];
        TextureRegion[][] tmp = TextureRegion.split(sheet, fw, fh);

        for (int i = 0; i < COLS; i++) frames[i] = tmp[0][i];

        anim = new Animation<>(0.09f, frames);
        anim.setPlayMode(Animation.PlayMode.LOOP);

        width = fw * 1.4f;
        height = fh * 1.4f;
    }

    @Override
    public void update(float delta, float px, float py) {
        if (!vivo) return;

        time += delta;

        float dx = px - x;
        float dy = py - y;
        float len = (float) Math.sqrt(dx*dx + dy*dy);

        if (len > 1) {
            dx /= len;
            dy /= len;
            x += dx * speed * delta;
            y += dy * speed * delta;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(anim.getKeyFrame(time), x, y, width, height);
    }

    @Override
    public void dispose() {
        sheet.dispose();
    }
}
