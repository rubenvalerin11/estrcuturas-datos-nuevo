package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MyGame;

public class LWJGL3Launcher {
    public static void main (String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Castlevania");
        config.setWindowedMode(900, 600);
        config.useVsync(true);
        new Lwjgl3Application(new MyGame(), config);
    }
}
