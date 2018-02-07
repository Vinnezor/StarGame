package ru.geekbrains.stargame.ships;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;


public class MainShip extends Sprite {

    private final float SHIPS_HEIGHT = 0.15f;
    private final float BOTTOM_MARGIN = 0.05f;
    private TextureRegion mainShipRegion;
    private Vector2 velocity;
    private final Vector2 velocityX = new Vector2(0.5f, 0);
    private Rect worldBounds;
    private Vector2 slowDown = new Vector2(0, -0.0003f);
    private float velocityMoveY = 0.015f;
    private boolean pressedLeft;
    private boolean pressedRight;



    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
//        cutRegion();
//        regions[0] = mainShipRegion;
        setHeightProportion(SHIPS_HEIGHT);
        velocity = new Vector2(pos);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

//    public void cutRegion() {
//        TextureRegion region = regions[0];
//        int cutWidth = region.getRegionWidth() / 2;
//        mainShipRegion = new TextureRegion(region, 0, 0, cutWidth, region.getRegionHeight());
//    }

    @Override
    public void update(float dt) {
        pos.mulAdd(velocity, dt);
        checkBounds();
    }

    public void setVelocity(float vx, float vy) {
        velocity.set(vx, vy);
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
        if (velocity.y > 0 || getBottom() > worldBounds.getBottom() + BOTTOM_MARGIN) velocity.add(slowDown);
        else {
            velocity.y = 0;
            setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        }
    }

    public void moveRight() {
        velocity.set(0.5f, velocity.y);
    }

    public void moveLeft() {
        velocity.set(-0.5f, velocity.y);
    }

    public void moveUp() {
        if(velocity.y == 0) velocity.set(velocity.x, velocityMoveY);
        else velocity.set(velocity.x, velocity.y);
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
        }
//        if(keycode == Input.Keys.UP) {
//            moveUp();
//        }
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
