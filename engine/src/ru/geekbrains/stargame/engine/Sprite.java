package ru.geekbrains.stargame.engine;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite (TextureRegion region){
        if(region == null)
            throw new NullPointerException("Пустой Textrure region");
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(), //точка отрисовки
                getHalfWidth(), getHalfWidth(), // точка вращения
                getWidth(), getHeight(), //ширина и высота
                scale, scale, // маштаб по х и у
                angle //угол вращения
                );
    }

    public void setWidthProportion(float width) {
        setWidth(width);
        float aspect = regions[frame].getRegionWidth() / regions[frame].getRegionHeight();
        setHeight(width / aspect);
    }

    public void setHeightProportion(float height) {

        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void resize(Rect worldBounds){

    }


    protected void touchDown(Vector2 touch, int pointer) {


    }


    protected void touchUp(Vector2 touch, int pointer) {


    }


    protected void touchDragged(Vector2 touch, int pointer) {


    }

    public void update(float dt) {

    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}