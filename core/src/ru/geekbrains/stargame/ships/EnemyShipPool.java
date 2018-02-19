package ru.geekbrains.stargame.ships;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.explosion.ExplosionPool;

public class EnemyShipPool extends SpritesPool<EnemyShip> {


    private final ExplosionPool explosion;
    private final SpritesPool weaponPool;


    private Rect worldBounds;
    private MainShip mainShip;


    public EnemyShipPool(SpritesPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
        this.weaponPool = bulletPool;
        this.explosion = explosionPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(weaponPool, explosion, worldBounds, mainShip);
    }





}
