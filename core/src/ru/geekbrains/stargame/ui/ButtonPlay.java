package ru.geekbrains.stargame.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;


public class ButtonPlay extends Sprite {

    public ButtonPlay(TextureRegion region) {
        super(region);
        setSize(0.2f, 0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setLeft(worldBounds.getLeft());
        setBottom(worldBounds.getBottom());
    }




}
