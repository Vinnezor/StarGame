package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.weapon.BulletPool;


public class MainShip extends Ship {

    private final float SHIPS_HEIGHT = 0.15f;
    private final float BOTTOM_MARGIN = 0.05f;
    private final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;
    private int pointerLeft = INVALID_POINTER;
    private int pointerRight = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound shipShootSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIPS_HEIGHT);
        this.velocityShipX = new Vector2(0.5f, 0);
        this.bullets = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletHeight = 0.01f;
        this.bulletVel.set(0, 0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.3f;
        this.shipShootSound = shipShootSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocity, dt);
        reloadTimer += dt;
        if(reloadTimer >= reloadInterval){
            reloadTimer = 0;
            shoot();
        }
        checkBounds();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if(pos.x > touch.x) {
            if(pointerLeft != INVALID_POINTER) return;
            pointerLeft = pointer;
            moveLeft();
        } else {
            if(pointerRight != INVALID_POINTER) return;
            pointerRight = pointer;
            moveRight();
        }
    }

    public void setBullets(BulletPool bullets) {
        this.bullets = bullets;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if(pointerLeft == pointer) {
            pointerLeft = INVALID_POINTER;
            if (pointerRight != INVALID_POINTER) moveRight();
            else moveStop();
        } else if (pointerRight == pointer) {
            pointerRight = INVALID_POINTER;
            if(pointerLeft != INVALID_POINTER) moveLeft();
            else moveStop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.W:
            case Input.Keys.UP:
                shoot();
                break;
        }
    }


    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight) {
                    moveRight();
                } else {
                    moveStop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft){
                    moveLeft();
                } else {
                    moveStop();
                }
                break;
        }
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
