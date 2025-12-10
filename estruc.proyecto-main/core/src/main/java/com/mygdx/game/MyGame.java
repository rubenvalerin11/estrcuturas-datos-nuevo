package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.view.MenuScreen;

public class MyGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
