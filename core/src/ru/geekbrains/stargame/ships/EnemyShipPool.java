package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.weapon.BulletPool;
import ru.geekbrains.stargame.weapon.Weapon;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private final ExplosionPool explosion;
    private final SpritesPool weaponPool;
    private final Sound shipShootSound;


    private Rect worldBounds;
    private MainShip mainShip;


    public EnemyShipPool(SpritesPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip, Sound shipShootSound) {
        this.weaponPool = bulletPool;
        this.shipShootSound = shipShootSound;
        this.explosion = explosionPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(weaponPool, explosion, worldBounds, mainShip, shipShootSound);
    }





}
