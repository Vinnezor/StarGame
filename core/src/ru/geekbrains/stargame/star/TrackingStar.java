package ru.geekbrains.stargame.star;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class TrackingStar extends Star {

    private final Vector2 trackingVel;

    private final Vector2 sumVelocity = new Vector2();

    public TrackingStar(TextureAtlas atlas, float vx, float vy, float height, Vector2 trackingVel) {
        super(atlas, vx, vy, height);
        this.trackingVel = trackingVel;
    }

    @Override
    public void update(float dt) {
        sumVelocity.setZero().mulAdd(trackingVel, 0.2f).rotate(180).add(v);
        pos.mulAdd(sumVelocity, dt);
        checkAndHandleBounds();
    }
}
