package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.utils.Regions;
import ru.geekbrains.stargame.weapon.BulletPool;

public class EnemyShip extends Ship {

    private final float SHIPS_HEIGHT = 0.15f;

    private float moveDownInterval;
    private float moveDownTimer;
    private Vector2 mainShipPos;
    private Vector2 velocityShipY;

    public EnemyShip () {}

    public void setPropEnemyShip(
            TextureAtlas atlas,
            String enemyTextureName,
            BulletPool bulletPool,
            Sound shipShootSound
            ) {
        this.regions = Regions.split(atlas.findRegion(enemyTextureName), 1, 2, 2);
        setHeightProportion(SHIPS_HEIGHT);
        this.velocityShipX = new Vector2(0.2f, 0);
        this.velocityShipY = new Vector2(0, -0.2f);
        this.bullets = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletHeight = 0.01f;
        this.bulletVel.set(0, -0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.6f;
        this.shipShootSound = shipShootSound;
        this.moveDownInterval = 0.2f;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getTop());

    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocity, dt);

        leftOrRightMove();
        //плавное вылезание из за экрана пока тут
        if(getTop() > worldBounds.getTop()) moveDown();
        else if (moveDownInterval <= (moveDownTimer += dt)){
            moveDownTimer = 0;
            moveDown();
        }
        checkBounds();
        reloadTimer += dt;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0;
            shoot();
        }
        if(getTop() < worldBounds.getBottom()) setDestroyed(true);
    }

    public void moveDown() {
        velocity.y = velocityShipY.y;
    }

    private void leftOrRightMove() {
        if(mainShipPos.x > pos.x) {
            moveRight();
        } else {
            moveLeft();
        }

    }

    public void setMainShipPos(Vector2 mainShipPos) {
        this.mainShipPos = mainShipPos;
    }

}
