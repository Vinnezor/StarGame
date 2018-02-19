package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.explosion.ExplosionPool;

public class EnemyShip extends Ship {

    private enum State{DESCENT, FIGHT}

    private MainShip mainShip;
    private Vector2 velocity0 = new Vector2();
    private Vector2 descentVel = new Vector2(0, -0.15f);
    private State state;



    public EnemyShip(
                     SpritesPool bulletPool,
                     ExplosionPool explosionPool,
                     Rect worldBounds,
                     MainShip mainShip
                ) {
        super(bulletPool, explosionPool, worldBounds);
        this.mainShip = mainShip;
        this.velocity.set(velocity0);
    }

    public void setPropEnemyShip(
            TextureRegion[] regions,
            Vector2 velocity0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int hp
            ) {
        this.regions = regions;
        this.velocity0.set(velocity0);
        this.weaponRegion = bulletRegion;
        this.weaponHeight = bulletHeight;
        this.weaponVel.set(0f, bulletVY);
        this.weaponDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        this.velocity.set(descentVel);
        state = State.DESCENT;
        reloadTimer = reloadInterval;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getTop());

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        switch (state) {
            case DESCENT:
                if(getTop() <= worldBounds.getTop()){
                    velocity.set(velocity0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                automaticFire(dt);
                if(getBottom() < worldBounds.getBottom()){
                    mainShip.damage(weaponDamage);
                    boom();
                    setDestroyed(true);
                }
                break;
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
                );
    }



}
