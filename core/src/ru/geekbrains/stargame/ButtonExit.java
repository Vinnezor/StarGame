package ru.geekbrains.stargame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;

public class ButtonExit extends Sprite {

    public ButtonExit(TextureRegion region) {
        super(region);
        setSize(0.2f, 0.2f);

    }

    @Override
    public void resize(Rect worldBounds) {
        setRight(worldBounds.getRight());
        setBottom(worldBounds.getBottom());
    }
}
