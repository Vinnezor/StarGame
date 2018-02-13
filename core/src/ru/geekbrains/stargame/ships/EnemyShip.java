package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.utils.Regions;
import ru.geekbrains.stargame.weapon.BulletPool;

public class EnemyShip extends Ship {

    private final float SHIPS_HEIGHT = 0.15f;

    public EnemyShip () {}

    public void setPropEnemyShip(
            TextureAtlas atlas,
            String enemyTextureName,
            BulletPool bulletPool,
            Sound shipShootSound
            ) {
        this.regions = Regions.split(atlas.findRegion(enemyTextureName), 1, 2, 2);
        setHeightProportion(SHIPS_HEIGHT);
        this.velocityShipX = new Vector2(0.3f, 0);
        this.bullets = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletHeight = 0.01f;
        this.bulletVel.set(0, -0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.3f;
        this.shipShootSound = shipShootSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop());
    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocityShipX, dt);
        checkBounds();
    }



}
