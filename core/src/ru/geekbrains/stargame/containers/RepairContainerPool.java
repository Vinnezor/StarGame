package ru.geekbrains.stargame.containers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;


public class RepairContainerPool extends SpritesPool<RepairContainer> {

    private TextureAtlas atlas;
    private float generateTimer;
    private float generateInterval;
    private Rect worldBounds;

    public RepairContainerPool(TextureAtlas atlas, Rect worldBounds) {
        this.atlas = atlas;
        this.worldBounds = worldBounds;
        this.generateInterval = 28f;
    }

    @Override
    protected RepairContainer newObject() {
        return new RepairContainer(atlas, worldBounds);
    }

    public void generateRepairContainers(float delta) {
        generateTimer += delta;
        if(generateInterval <= generateTimer) {
            generateTimer = 0;
            RepairContainer repairContainer = obtain();
            repairContainer.setRepairContainerProperties();
        }
    }

    @Override
    public void updateActiveObjects(float dt) {
        super.updateActiveObjects(dt);
        generateRepairContainers(dt);
    }
}
