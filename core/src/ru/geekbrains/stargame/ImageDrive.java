package ru.geekbrains.stargame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class ImageDrive {

    private Vector2 position;
    private Vector2 velosity;
    private TextureRegion region;
    private Texture img;
    private int imgHeight;
    private int imgWidth;
    private float vx = 100f;
    private float vy = 100f;

    ImageDrive() {
        imgWidth = 100;
        imgHeight = 100;
        int startX = (int) (Math.random() * (StarGame.WIDTH_WINDOW - imgWidth));
        int startY = (int) (Math.random() * (StarGame.HEIGHT_WINDOW - imgHeight));
        position = new Vector2(startX, startY);
        velosity = new Vector2(0, 0);
        img = new Texture("badlogic.jpg");
        region = new TextureRegion(img, 40, 40, imgWidth, imgHeight);
    }

    public void checkBorder(float x, float y) {
        boolean isXBorder = x < 0 || (x + region.getRegionWidth()) > StarGame.WIDTH_WINDOW;
        boolean isYBorder = y < 0 || (y + region.getRegionHeight()) > StarGame.HEIGHT_WINDOW;
        if(isXBorder) {
            vx = -vx;
        }
        if(isYBorder) {
            vy = -vy;
        }
    }

    public void update(float dt) {
        checkBorder(position.x, position.y);
        velosity.add(vx, vy);
        velosity.scl(dt);
        position.add(velosity);
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getRegion() {
        return region;
    }

}
