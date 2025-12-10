package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class BossDracula extends Enemy {

    private enum State {
        PHASE1_IDLE,
        PHASE1_ATTACK,
        TRANSFORM,
        PHASE2_IDLE,
        PHASE2_ATTACK
    }

    private State state = State.PHASE1_IDLE;

    private Texture texIdle1;
    private Texture texAtk1;
    private Texture texTransform;
    private Texture texIdle2;
    private Texture texAtk2;

    private Animation<TextureRegion> animIdle1;
    private Animation<TextureRegion> animAtk1;
    private Animation<TextureRegion> animTransform;
    private Animation<TextureRegion> animIdle2;
    private Animation<TextureRegion> animAtk2;

    private float stateTime = 0f;
    private float attackCooldown = 1.5f;
    private boolean shotSpawned = false;

    private boolean facingRight = false;

    // Fireball pendiente para que el GameController la recoja
    private DraculaFireball pendingShot = null;

    public BossDracula(float x, float y) {
        super(x, y, 90f, 220, 30); // speed, vida, daño por contacto

        // ===== Carga de sprites =====
        texIdle1      = new Texture("draculadepie.png");      // 4
        texAtk1       = new Texture("ataquedracula.png");     // 6
        texTransform  = new Texture("draculaconvierte.png");  // 6
        texIdle2      = new Texture("2dafasedra.png");        // 3
        texAtk2       = new Texture("2dafaseataque.png");     // 5

        animIdle1     = buildAnimation(texIdle1, 4, 0.18f, Animation.PlayMode.LOOP);
        animAtk1      = buildAnimation(texAtk1, 6, 0.14f, Animation.PlayMode.NORMAL);
        animTransform = buildAnimation(texTransform, 6, 0.16f, Animation.PlayMode.NORMAL);
        animIdle2     = buildAnimation(texIdle2, 3, 0.18f, Animation.PlayMode.LOOP);
        animAtk2      = buildAnimation(texAtk2, 5, 0.14f, Animation.PlayMode.NORMAL);

        // tamaño base del jefe
        TextureRegion first = animIdle1.getKeyFrame(0);
        this.width  = first.getRegionWidth()  * 2.1f;
        this.height = first.getRegionHeight() * 2.1f;
    }

    private Animation<TextureRegion> buildAnimation(Texture sheet,
                                                    int cols,
                                                    float frameDuration,
                                                    Animation.PlayMode mode) {
        int fw = sheet.getWidth() / cols;
        int fh = sheet.getHeight();

        TextureRegion[][] tmp = TextureRegion.split(sheet, fw, fh);
        Array<TextureRegion> arr = new Array<>(cols);
        for (int i = 0; i < cols; i++) {
            arr.add(tmp[0][i]);
        }

        Animation<TextureRegion> anim =
            new Animation<>(frameDuration, arr, mode);
        return anim;
    }

    @Override
    public void update(float delta, float playerX, float playerY) {
        if (!vivo) return;

        stateTime += delta;
        attackCooldown -= delta;

        // orientar hacia el jugador
        facingRight = (playerX > x);

        // ¿cambia a fase 2?
        if (vida <= vidaMaxima / 2 && state != State.TRANSFORM &&
            state != State.PHASE2_IDLE && state != State.PHASE2_ATTACK) {
            state = State.TRANSFORM;
            stateTime = 0f;
        }

        switch (state) {
            case PHASE1_IDLE:
                phase1Idle(delta);
                break;
            case PHASE1_ATTACK:
                phase1Attack(delta, playerX, playerY);
                break;
            case TRANSFORM:
                phaseTransform();
                break;
            case PHASE2_IDLE:
                phase2Idle(delta, playerX);
                break;
            case PHASE2_ATTACK:
                phase2Attack();
                break;
        }
    }

    private void phase1Idle(float delta) {
        // en fase 1 casi no se mueve
        if (attackCooldown <= 0f) {
            state = State.PHASE1_ATTACK;
            stateTime = 0f;
            shotSpawned = false;
        }
    }

    private void phase1Attack(float delta, float playerX, float playerY) {
        // dispara bola de fuego una sola vez en medio de la animación
        if (!shotSpawned && stateTime > 0.3f) {
            float sx = facingRight ? x + width * 0.6f : x + width * 0.4f;
            float sy = y + height * 0.6f;
            pendingShot = new DraculaFireball(sx, sy, playerX, playerY);
            shotSpawned = true;
        }

        if (stateTime > animAtk1.getAnimationDuration()) {
            state = State.PHASE1_IDLE;
            stateTime = 0f;
            attackCooldown = 2.0f + MathUtils.random(1.5f);
        }
    }

    private void phaseTransform() {
        if (stateTime > animTransform.getAnimationDuration()) {
            state = State.PHASE2_IDLE;
            stateTime = 0f;
            attackCooldown = 1.2f;
        }
    }

    private void phase2Idle(float delta, float playerX) {
        // Camina lentamente hacia el jugador
        if (playerX < x - 10) {
            x -= speed * 0.6f * delta;
        } else if (playerX > x + 10) {
            x += speed * 0.6f * delta;
        }

        float distX = Math.abs(playerX - x);
        if (distX < 120 && attackCooldown <= 0f) {
            state = State.PHASE2_ATTACK;
            stateTime = 0f;
        }
    }

    private void phase2Attack() {
        if (stateTime > animAtk2.getAnimationDuration()) {
            state = State.PHASE2_IDLE;
            stateTime = 0f;
            attackCooldown = 0.9f;
        }
        // El daño cuerpo a cuerpo lo maneja GameController por colisión
    }

    // GameController llama a esto para ver si hay fireball nueva
    public DraculaFireball consumeNewShot() {
        DraculaFireball out = pendingShot;
        pendingShot = null;
        return out;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (!vivo) return;

        Animation<TextureRegion> anim;
        switch (state) {
            case PHASE1_ATTACK:  anim = animAtk1;      break;
            case TRANSFORM:      anim = animTransform; break;
            case PHASE2_IDLE:    anim = animIdle2;     break;
            case PHASE2_ATTACK:  anim = animAtk2;      break;
            case PHASE1_IDLE:
            default:             anim = animIdle1;     break;
        }

        TextureRegion frame = anim.getKeyFrame(stateTime);
        float drawX = x;
        float drawW = width;

        if (!facingRight) {
            drawX = x + width;
            drawW = -width;
        }

        batch.draw(frame, drawX, y, drawW, height);
    }

    @Override
    public void dispose() {
        texIdle1.dispose();
        texAtk1.dispose();
        texTransform.dispose();
        texIdle2.dispose();
        texAtk2.dispose();
    }
}
