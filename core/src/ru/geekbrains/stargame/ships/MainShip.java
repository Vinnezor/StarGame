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
    private Vector2 slowDown = new Vector2(0, -0.0003f);
    private float velocityMoveY = 0.015f;


    public MainShip(TextureAtlas atlas, float height) {
        super(atlas.findRegion("main_ship"));
        cutRegion();
        setHeightProportion(height);
        regions[0] = mainShipRegion;
        velocity = new Vector2(pos);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom());
    }

    public void cutRegion() {
        TextureRegion region = regions[0];
        int cutWidth = region.getRegionWidth() / 2;
        mainShipRegion = new TextureRegion(region, 0, 0, cutWidth, region.getRegionHeight());
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
        else if (getRight() > worldBounds.getRight()) setRight(worldBounds.getRight());
        if (velocity.y > 0 || getBottom() > worldBounds.getBottom()) velocity.add(slowDown);
        else {
            velocity.y = 0;
            setBottom(worldBounds.getBottom());
        }
    }

    public void moveRight() {
        velocity.set(0.01f, velocity.y);
    }

    public void moveLeft() {
        velocity.set(-0.01f, velocity.y);
    }

    public void moveUp() {
        if(velocity.y == 0) velocity.set(velocity.x, velocityMoveY);
        else velocity.set(velocity.x, velocity.y);
    }


    public void moveStop() {
        velocity.set(0, velocity.y);
    }




}
