package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.weapon.BulletPool;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private TextureAtlas atlas;
    private String[] namesShipsTextures;
    private BulletPool bulletPool;
    private Rect worldBounds;
    private Sound shipShootSound;
    private MainShip mainShip;
    private float timeToNewShip;
    private float intervalToNewShip;


    public EnemyShipPool(TextureAtlas atlas, String[] namesShipsTextures, BulletPool bulletPool, Sound shipShootSound) {
        this.atlas = atlas;
        this.namesShipsTextures = namesShipsTextures;
        this.bulletPool = bulletPool;
        this.shipShootSound = shipShootSound;
        this.intervalToNewShip = 20f;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip();
    }

    private void createNewEnemyShip() {
        EnemyShip enemy =  obtain();
        enemy.setPropEnemyShip(atlas, rndEnemyShip(), bulletPool, shipShootSound);
        enemy.resize(worldBounds);
        enemy.setMainShipPos(mainShip.pos);
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    private boolean isEnemyShipOnScreen() {
        if(activeObjects.isEmpty()) return false;
        return true;
    }

    public void addEnemyShip (float dt) {
        if (!isEnemyShipOnScreen()) createNewEnemyShip();
        if(intervalToNewShip <= (timeToNewShip += dt)){
            createNewEnemyShip();
            timeToNewShip = 0;
        }

    }

    private String rndEnemyShip (){
        return namesShipsTextures[(int) Rnd.nextFloat(0, namesShipsTextures.length)];
    }

    public void setMainShip(MainShip mainShip) {
        this.mainShip = mainShip;
    }
}
