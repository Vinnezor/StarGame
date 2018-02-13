package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.weapon.Bullet;
import ru.geekbrains.stargame.weapon.BulletPool;

public abstract class Ship extends Sprite {

    protected final Vector2 velocity = new Vector2(); //скорость корабля
    protected final Vector2 bulletVel = new Vector2(); // скорость пули

    protected Vector2 velocityShipX;
    protected Vector2 velocityShipY;
    protected Rect worldBounds; //границы мира
    protected BulletPool bullets;
    protected TextureRegion bulletRegion;

    protected float bulletHeight;
    protected float reloadInterval;
    protected float reloadTimer;

    protected int bulletDamage;
    protected Sound shipShootSound;

    public Ship() {

    }

    public Ship (TextureRegion region, int rows, int cols, int frame) {
       super(region, rows, cols, frame);
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

    public void shoot() {
        Bullet bullet = bullets.obtain();
        bullet.setBulletProp(this, bulletRegion, this.pos, bulletVel, bulletHeight, worldBounds, bulletDamage, shipShootSound);
    }
}
