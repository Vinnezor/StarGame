package ru.geekbrains.stargame.containers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;

public class Container extends Sprite {

    private final Vector2 velocity = new Vector2();
    private Rect worldBounds;

    public Container(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("containerYellow1"));
        this.worldBounds = worldBounds;

    }

    public void setContainerProperties() {
        float height = 0.06f;
        setHeightProportion(height);
        velocity.set(0, -0.5f);
        setBottom(worldBounds.getTop());
        pos.x = Rnd.nextFloat(worldBounds.getLeft() + getWidth(), worldBounds.getRight() - getWidth());
    }

    @Override
    public void update(float dt) {
        this.pos.mulAdd(velocity, dt);
        if(isOutside(worldBounds)) setDestroyed(true);
    }

}
