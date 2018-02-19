package ru.geekbrains.stargame.weapon;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.math.Rect;

public interface Weapon {

    void setProperties(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 velocity0,
            float height,
            Rect worldBounds,
            int damage
    );

    void update(float dt);
    int getDamage();

    Object getOwner();
    boolean isDestroyed();
    void setDestroyed(boolean destroyed);
    Rect getRect();
}
