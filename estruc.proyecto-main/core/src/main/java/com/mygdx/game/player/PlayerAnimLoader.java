package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimLoader {

    public static Animation<TextureRegion> loadHorizontalSheet(String path, int frames, float speed) {
        Texture sheet = new Texture(path);
        int frameWidth = sheet.getWidth() / frames;
        int frameHeight = sheet.getHeight();

        TextureRegion[][] temp = TextureRegion.split(sheet, frameWidth, frameHeight);

        TextureRegion[] animFrames = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            animFrames[i] = temp[0][i];
        }
        return new Animation<>(speed, animFrames);
    }
}
