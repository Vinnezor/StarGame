package ru.geekbrains.stargame.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.stargame.engine.ActionListener;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.ui.ScaledTouchUpButton;


public class ButtonPlay extends ScaledTouchUpButton {

    public ButtonPlay(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), pressScale, actionListener);

    }

    @Override
    public void resize(Rect worldBounds) {
        setWidth(getHeight()); // костыль при обьявлении не устанавливает Width *TODO
        setLeft(worldBounds.getLeft());
        setBottom(worldBounds.getBottom());

    }

}


