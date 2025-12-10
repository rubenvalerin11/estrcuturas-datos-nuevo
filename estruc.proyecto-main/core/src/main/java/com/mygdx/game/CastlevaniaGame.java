package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.view.MenuScreen;

public class CastlevaniaGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        if (getScreen() != null) getScreen().dispose();
    }
}
