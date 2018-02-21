package ru.geekbrains.stargame.containers;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class ContainerPool extends SpritesPool<Container> {

    private TextureAtlas atlas;
    private float generateTimer;
    private float generateInterval;
    private Rect worldBounds;

    public ContainerPool(TextureAtlas atlas, Rect worldBounds) {
        this.atlas = atlas;
        this.worldBounds = worldBounds;
        this.generateInterval = 20f;
    }

    @Override
    protected Container newObject() {
        return new Container(atlas, worldBounds);
    }

    public void generateContainers(float delta) {
        generateTimer += delta;
        if(generateInterval <= generateTimer) {
            generateTimer = 0;
            Container container = obtain();
            container.setContainerProperties();
        }
    }

    @Override
    public void updateActiveObjects(float dt) {
        super.updateActiveObjects(dt);
        generateContainers(dt);
    }
}
