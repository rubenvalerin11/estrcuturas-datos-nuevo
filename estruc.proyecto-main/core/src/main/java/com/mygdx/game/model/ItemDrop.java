package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Item visible en el mapa (para las espadas).
 * NO está ligado al inventario todavía, solo a la lógica del jugador.
 */
public class ItemDrop {

    private float x, y;
    private Texture texture;
    private Rectangle bounds;
    private WeaponType weaponType;
    private boolean collected = false;

    public ItemDrop(String textureName, float x, float y, WeaponType type) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(textureName);
        this.weaponType = type;
        this.bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
        // Opcional: podrías hacer texture.dispose() aquí si ya no se usa más.
    }

    public void dispose() {
        texture.dispose();
    }
}
