package ru.geekbrains.stargame.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.engine.math.MatrixUtils;
import ru.geekbrains.stargame.engine.math.Rect;


public class Base2DScreen implements Screen, InputProcessor {

    protected Game game;
    private Rect screenBounds; //границы области рисования в пикселях
    private Rect worldBounds; //границы проекции мировых координат
    private Rect glBounds; //дефолтные проекции мира gl

    protected Matrix4 worldGl;
    protected Matrix3 screenToWorld;

    protected SpriteBatch batch;
    private final Vector2 touch = new Vector2();

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown - " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp - " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped - " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        System.out.println("touchDown - screenX > " + touch.x + " screenY > " + touch.y );
        touchDown(touch, pointer);
        return false;
    }


    protected void touchDown(Vector2 touch, int pointer) {


    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        System.out.println("touchUp - screenX > " + touch.x + " screenY > " + touch.y );
        touchUp(touch, pointer);
        return false;
    }

    protected void touchUp(Vector2 touch, int pointer) {

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        System.out.println("touchDragged - screenX > " + touch.x + " screenY > " + touch.y );
        touchDragged(touch, pointer);
        return false;
    }

    protected void touchDragged(Vector2 touch, int pointer) {

    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        if(batch != null)
            throw new RuntimeException("batch != null Повторная установка screen без dispose");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {

    }



    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);

    }

    protected void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        batch.dispose();
        batch = null;
    }

}
