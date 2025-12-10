package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DraculaFireball {

    private static Texture sheet;
    private static TextureRegion[] frames;
    private static int COLS = 8; // frames en draculaefecto.png

    private Animation<TextureRegion> anim;
    private float stateTime = 0f;

    private float x, y;
    private float vx, vy;
    private float speed = 260f;

    private float width;
    private float height;

    private boolean alive = true;

    public DraculaFireball(float startX, float startY,
                           float targetX, float targetY) {

        if (sheet == null) {
            sheet = new Texture("draculaefecto.png");
            int fw = sheet.getWidth() / COLS;
            int fh = sheet.getHeight();

            TextureRegion[][] tmp = TextureRegion.split(sheet, fw, fh);
            frames = new TextureRegion[COLS];
            for (int i = 0; i < COLS; i++) {
                frames[i] = tmp[0][i];
            }
        }

        Array<TextureRegion> arr = new Array<>(frames);
        anim = new Animation<TextureRegion>(0.06f, arr, Animation.PlayMode.LOOP);

        width = frames[0].getRegionWidth();
        height = frames[0].getRegionHeight();

        this.x = startX;
        this.y = startY;

        Vector2 dir = new Vector2(targetX - startX, targetY - startY);
        if (dir.len() == 0) dir.set(1, 0);
        dir.nor();

        vx = dir.x * speed;
        vy = dir.y * speed;
    }

    public void update(float delta) {
        if (!alive) return;

        stateTime += delta;
        x += vx * delta;
        y += vy * delta;

        // Si sale de pantalla, muere
        if (x < -100 || x > 1400 || y < -100 || y > 900) {
            alive = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (!alive) return;
        TextureRegion frame = anim.getKeyFrame(stateTime);
        batch.draw(frame, x, y);
    }

    public boolean isAlive() { return alive; }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public static void disposeShared() {
        if (sheet != null) {
            sheet.dispose();
            sheet = null;
        }
    }
}
