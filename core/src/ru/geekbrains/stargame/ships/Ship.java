package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.explosion.Explosion;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.screen.GameScreen;
import ru.geekbrains.stargame.weapon.Weapon;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

    protected final Vector2 weaponVel = new Vector2(); // скорость пули
    protected Vector2 velocity = new Vector2(); //скорость корабля

    protected Vector2 velocityShipX;
    protected Rect worldBounds; //границы мира
    protected SpritesPool weaponPool;

    protected ExplosionPool explosionShip;
    protected TextureRegion weaponRegion;

    protected float weaponHeight;
    protected float reloadInterval;
    protected float reloadTimer;


    protected int weaponDamage;
    protected int hp; // жизнь корабля


    public Ship (TextureRegion region,
                 int rows,
                 int cols,
                 int frame,
                 SpritesPool weaponPool,
                 ExplosionPool explosionPool,
                 Rect worldBounds
                ) {
       super(region, rows, cols, frame);
        this.weaponPool = weaponPool;
        this.explosionShip = explosionPool;
        this.worldBounds = worldBounds;

    }

    public Ship(SpritesPool weaponPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.weaponPool = weaponPool;
        this.explosionShip = explosionPool;
        this.worldBounds = worldBounds;
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
        Weapon weapon = (Weapon) weaponPool.obtain();
        weapon.setProperties(this, weaponRegion, this.pos, weaponVel, weaponHeight, worldBounds, weaponDamage);
        weapon.soundPlay();
    }

    public void boom() {
        hp = 0;
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
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
        if (hp == 0) {
            setDestroyed(true);
            boom();
        }


    }


    public int getHp() {
        return hp;
    }


}
