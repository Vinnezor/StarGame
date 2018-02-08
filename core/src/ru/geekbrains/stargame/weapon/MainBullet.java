package ru.geekbrains.stargame.weapon;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;

public class MainBullet extends Sprite {

    private final float BULLET_HEIGHT = 0.01f;
    private Vector2 velocity;
    private final float vy = 0.5f;
    private Rect worldBounds;
    private TextureAtlas atlas;



    public MainBullet(TextureAtlas atlas) {
        super(atlas.findRegion("bulletMainShip"));
        setHeightProportion(BULLET_HEIGHT);
        velocity = new Vector2(0, vy);
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocity, dt);

    }


}
