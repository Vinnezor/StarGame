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

    protected Rect worldBounds; //границы мира
    protected BulletPool bullets;
    protected TextureRegion bulletRegion;

    protected float bulletHeight;
    protected float reloadInterval;
    protected float reloadTimer;

    protected int bulletDamage;
    protected Sound shipShootSound;

    public Ship (TextureRegion region, int rows, int cols, int frame) {
       super(region, rows, cols, frame);
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
