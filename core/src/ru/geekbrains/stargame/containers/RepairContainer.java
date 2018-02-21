package ru.geekbrains.stargame.containers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;

public class RepairContainer extends Sprite{

    private final Vector2 velocity = new Vector2();
    private Rect worldBounds;
    private int restoreHP;

    public RepairContainer(TextureAtlas atlas, Rect worldBounds) {
        super(atlas.findRegion("repair1"));
        this.worldBounds = worldBounds;

    }

    public void setRepairContainerProperties() {
        float height = 0.06f;
        restoreHP = 20;
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

    public int getRestoreHP() {
        return restoreHP;
    }
}
