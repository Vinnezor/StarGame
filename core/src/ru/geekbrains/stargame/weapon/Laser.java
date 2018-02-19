package ru.geekbrains.stargame.weapon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;

public class Laser extends Sprite implements Weapon {

    private final Vector2 velocity = new Vector2();
    private Rect worldBounds;
    private int damage;
    private Object owner;

    public Laser() {
        regions = new TextureRegion[1];
    }

    public void setProperties(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 velocity0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.velocity.set(velocity0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float dt) {
        this.pos.mulAdd(velocity, dt);
        if(isOutside(worldBounds)) setDestroyed(true);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }

    public Rect getRect() {
        return this;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
