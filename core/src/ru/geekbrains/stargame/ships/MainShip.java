package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.weapon.MainBullet;


public class MainShip extends Sprite {

    private final float SHIPS_HEIGHT = 0.15f;
    private final float BOTTOM_MARGIN = 0.05f;
    private Vector2 velocity;
    private final Vector2 velocityX;
    private Rect worldBounds;
    private boolean pressedLeft;
    private boolean pressedRight;
    private TextureAtlas atlas;
    private MainBullet bullet;


    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.atlas = atlas;
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

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if(worldBounds.pos.x > touch.x) {
            moveLeft();
        } else {
            moveRight();
        }
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
