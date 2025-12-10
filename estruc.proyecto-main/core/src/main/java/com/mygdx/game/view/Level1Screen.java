package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.mygdx.game.MyGame;

public class Level1Screen implements Screen {

    private final MyGame game;

    public Level1Screen(MyGame game) {
        this.game = game;
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        // Este nivel ahora se maneja desde GameScreen.
        game.setScreen(new GameScreen(game));
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
