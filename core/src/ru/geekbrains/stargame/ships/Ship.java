package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.explosion.Explosion;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.weapon.Bullet;
import ru.geekbrains.stargame.weapon.BulletPool;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected final Vector2 bulletVel = new Vector2(); // скорость пули
    protected Vector2 velocity = new Vector2(); //скорость корабля


    protected Vector2 velocityShipX;
    protected Rect worldBounds; //границы мира
    protected BulletPool bullets;
    protected ExplosionPool explosionShip;
    protected TextureRegion bulletRegion;
    protected Sound shipShootSound;

    protected float bulletHeight;
    protected float reloadInterval;
    protected float reloadTimer;


    protected int bulletDamage;
    protected int hp; // жизнь корабля


    public Ship (TextureRegion region,
                 int rows,
                 int cols,
                 int frame,
                 BulletPool bulletPool,
                 ExplosionPool explosionPool,
                 Rect worldBounds,
                 Sound shipShootSound
                ) {
       super(region, rows, cols, frame);
        this.bullets = bulletPool;
        this.explosionShip = explosionPool;
        this.worldBounds = worldBounds;
        this.shipShootSound = shipShootSound;
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound shipShootSound) {
        this.bullets = bulletPool;
        this.explosionShip = explosionPool;
        this.worldBounds = worldBounds;
        this.shipShootSound = shipShootSound;
    }

    public void checkBounds() {
        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            moveStop();
        }
        else if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            moveStop();
        }
    }

    public void moveRight() {
        velocity.set(velocityShipX);
    }

    public void moveLeft() {
        velocity.set(velocityShipX).rotate(180);
    }


    public void moveStop() {
        velocity.setZero();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        pos.mulAdd(velocity, dt);
        damageAnimateTimer += dt;
        if(damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public void shoot() {
        Bullet bullet = bullets.obtain();
        bullet.setBulletProp(this, bulletRegion, this.pos, bulletVel, bulletHeight, worldBounds, bulletDamage);
        shipShootSound.play();
    }

    public void boom() {
        Explosion explosion = explosionShip.obtain();
        explosion.setExplosionSize(getHeight(), pos);
    }

    public void automaticFire(float dt){
        reloadTimer += dt;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0;
            shoot();
        }
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }
}
