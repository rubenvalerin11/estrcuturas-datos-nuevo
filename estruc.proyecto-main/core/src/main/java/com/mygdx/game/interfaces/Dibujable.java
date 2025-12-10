package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Dibujable {
    void render(SpriteBatch batch);
    void update(float delta);
}
