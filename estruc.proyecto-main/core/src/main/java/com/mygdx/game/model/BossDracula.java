package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BossDracula extends Enemy {

    private Texture idleSheet;
    private Texture transformSheet;
    private Texture attackSheet;

    private Animation<TextureRegion> idleAnim;
    private Animation<TextureRegion> transformAnim;
    private Animation<TextureRegion> attackAnim;

    private float time = 0f;
    private float attackCooldown = 0f;

    private enum State { IDLE, TRANSFORM, ATTACKING }
    private State state = State.IDLE;

    public BossDracula(float x, float y) {
        super(x, y, 50f, 200, 25); // speed, vida, daño

        // ========================
        //    IDLE (4 frames)
        // ========================
        idleSheet = new Texture("draculadepie.png");
        int IDLE_COLS = 4;
        int idleFrameW = idleSheet.getWidth() / IDLE_COLS;
        int idleFrameH = idleSheet.getHeight();

        TextureRegion[][] iTmp = TextureRegion.split(idleSheet, idleFrameW, idleFrameH);
        TextureRegion[] iFrames = new TextureRegion[IDLE_COLS];
        for (int i = 0; i < IDLE_COLS; i++) iFrames[i] = iTmp[0][i];
        idleAnim = new Animation<>(0.15f, iFrames);
        idleAnim.setPlayMode(Animation.PlayMode.LOOP);

        // ========================
        //  TRANSFORM (6 frames)
        // ========================
        transformSheet = new Texture("draculaconvierte.png");
        int TRANS_COLS = 6;
        int tFrameW = transformSheet.getWidth() / TRANS_COLS;
        int tFrameH = transformSheet.getHeight();

        TextureRegion[][] tTmp = TextureRegion.split(transformSheet, tFrameW, tFrameH);
        TextureRegion[] tFrames = new TextureRegion[TRANS_COLS];
        for (int i = 0; i < TRANS_COLS; i++) tFrames[i] = tTmp[0][i];

        transformAnim = new Animation<>(0.14f, tFrames);

        // ========================
        //   ATTACK (7 frames)
        // ========================
        attackSheet = new Texture("ataquedracula.png");
        int ATK_COLS = 7;
        int aFrameW = attackSheet.getWidth() / ATK_COLS;
        int aFrameH = attackSheet.getHeight();

        TextureRegion[][] aTmp = TextureRegion.split(attackSheet, aFrameW, aFrameH);
        TextureRegion[] aFrames = new TextureRegion[ATK_COLS];
        for (int i = 0; i < ATK_COLS; i++) aFrames[i] = aTmp[0][i];

        attackAnim = new Animation<>(0.12f, aFrames);

        // Tamaño visual del boss
        width = idleFrameW * 2.2f;
        height = idleFrameH * 2.2f;
    }

    @Override
    public void update(float delta, float px, float py) {
        if (!vivo) return;

        time += delta;
        attackCooldown -= delta;

        // Movimiento simple hacia el jugador
        float dx = px - x;
        float dy = py - y;
        float len = (float)Math.sqrt(dx*dx + dy*dy);

        if (len > 1) {
            dx /= len;
            dy /= len;
            x += dx * speed * delta;
            y += dy * speed * delta;
        }

        // ============================
        //      FASE 1: IDLE
        // ============================
        if (state == State.IDLE) {
            if (vida < 150) {
                state = State.TRANSFORM;
                time = 0f;
            }
            if (attackCooldown <= 0) startAttack();
        }

        // ============================
        //  FASE 2: TRANSFORMACIÓN
        // ============================
        if (state == State.TRANSFORM) {
            if (transformAnim.isAnimationFinished(time)) {
                state = State.ATTACKING;
                time = 0f;
            }
        }

        // ============================
        //     FASE 3: ATAQUE
        // ============================
        if (state == State.ATTACKING) {
            if (attackAnim.isAnimationFinished(time)) {
                state = State.IDLE;
                attackCooldown = 2f; // 2 segundos entre ataques
                time = 0f;
            }
        }
    }

    private void startAttack() {
        state = State.ATTACKING;
        time = 0f;
        attackCooldown = 2.5f;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame;

        switch (state) {
            case TRANSFORM:
                frame = transformAnim.getKeyFrame(time);
                break;
            case ATTACKING:
                frame = attackAnim.getKeyFrame(time);
                break;
            default:
                frame = idleAnim.getKeyFrame(time);
                break;
        }

        batch.draw(frame, x, y, width, height);
    }

    @Override
    public void dispose() {
        idleSheet.dispose();
        transformSheet.dispose();
        attackSheet.dispose();
    }
}
