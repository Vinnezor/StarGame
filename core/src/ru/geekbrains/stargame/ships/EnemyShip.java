package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.weapon.BulletPool;

public class EnemyShip extends Ship {

    private enum State{DESCENT, FIGHT}

    private MainShip mainShip;
    private Vector2 velocity0 = new Vector2();
    private Vector2 descentVel = new Vector2(0, -0.15f);
    private State state;



    public EnemyShip(
                     BulletPool bulletPool,
                     ExplosionPool explosionPool,
                     Rect worldBounds,
                     MainShip mainShip,
                     Sound shipShootSound
                ) {
        super(bulletPool, explosionPool, worldBounds, shipShootSound);
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
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletVel.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.shipSoundVolume = 1;
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
                    mainShip.damage(bulletDamage);
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
