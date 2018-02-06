package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;


public class MainShip extends Sprite {

    private TextureRegion mainShipRegion;
    private Vector2 velocity;
    private Rect worldBounds;


    public MainShip(TextureAtlas atlas, float height) {
        super(atlas.findRegion("main_ship"));
        cuteRegion();
        setHeightProportion(height);
        regions[0] = mainShipRegion;
        velocity = new Vector2(pos);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom());
    }

    public void cuteRegion() {
        TextureRegion region = regions[0];
        int cuteWidth = region.getRegionWidth() / 2;
        mainShipRegion = new TextureRegion(region, 0, 0, cuteWidth, region.getRegionHeight());
    }

    @Override
    public void update(float dt) {
        checkBounds();
        pos.add(velocity);
    }

    public void setVelocity(float vx, float vy) {
        velocity.set(vx, vy);
    }

    public void checkBounds() {
        if (getLeft() < worldBounds.getLeft()) setLeft(worldBounds.getLeft());
        if (getRight() > worldBounds.getRight()) setRight(worldBounds.getRight());
    }


}
