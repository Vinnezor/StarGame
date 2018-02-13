package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.weapon.BulletPool;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private TextureAtlas atlas;
    private String[] namesShipsTextures;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound shipShootSound;


    public EnemyShipPool(TextureAtlas atlas, String[] namesShipsTextures, BulletPool bulletPool, Sound shipShootSound) {
        this.atlas = atlas;
        this.namesShipsTextures = namesShipsTextures;
        this.bulletPool = bulletPool;
        this.shipShootSound = shipShootSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }

    private void createNewEnemyShip() {
        EnemyShip enemy =  obtain();
        enemy.setPropEnemyShip(atlas, "enemy2", bulletPool, shipShootSound);
        enemy.resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    private boolean isEnemyShipOnScreen() {
        if(activeObjects.isEmpty()) return true;
        return false;
    }

    public void addEnemyShip () {
        if (isEnemyShipOnScreen()) createNewEnemyShip();
    }

}
