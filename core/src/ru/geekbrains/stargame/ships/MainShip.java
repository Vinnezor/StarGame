package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.planet.Planet;
import ru.geekbrains.stargame.weapon.MainBullet;
import ru.geekbrains.stargame.weaponPools.MainBulletPool;


public class MainShip extends Sprite {

    private final float SHIPS_HEIGHT = 0.15f;
    private final float BOTTOM_MARGIN = 0.05f;
    private Vector2 velocity;
    private final Vector2 velocityX;
    private Rect worldBounds;
    private boolean pressedLeft;
    private boolean pressedRight;
    private MainBulletPool bullets;


    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIPS_HEIGHT);
        velocity = new Vector2();
        velocityX = new Vector2(0.5f, 0);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocity, dt);


        checkBounds();
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
        velocity.set(velocityX);
    }

    public void moveLeft() {
        velocity.set(velocityX).rotate(180);
    }


    public void moveStop() {
        velocity.setZero();
    }

    public void fire() {
        MainBullet bullet = bullets.obtain();
        bullet.pos.set(pos);
        bullet.setBottom(getTop());
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        System.out.println(getClass().getCanonicalName() + " pointer - " + pointer + " touch " + touch );

        if(worldBounds.pos.x > touch.x) {
            pressedLeft = true;
            moveLeft();
        } else {
            pressedRight = true;
            moveRight();
        }
    }

    public void setBullets(MainBulletPool bullets) {
        this.bullets = bullets;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        moveStop();
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
                fire();
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


}
