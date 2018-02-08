package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.ships.MainShip;
import ru.geekbrains.stargame.star.Star;
import ru.geekbrains.stargame.weaponPools.MainBulletPool;


public class GameScreen extends Base2DScreen {

    private final int COUNT_STARS_ON_SCREEN = 20;

    private Texture bgTexture;
    private TextureAtlas mainAtlas;
    private Background background;
    private MainShip mainShip;
    private ArrayList<Star> stars;
    private MainBulletPool bullets;


    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("gameBG.png");
        background = new Background(new TextureRegion(bgTexture));
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        stars = new ArrayList<Star>(COUNT_STARS_ON_SCREEN);
        mainShip = new MainShip(mainAtlas);
        for (int i = 0; i < COUNT_STARS_ON_SCREEN ; i++) {
            stars.add(new Star(mainAtlas, Rnd.nextFloat(-0.05f, 0.05f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f));
        }
        bullets = new MainBulletPool(mainAtlas);
        mainShip.setBullets(bullets);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
        update(delta);
    }


    public void update(float delta) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).update(delta);
        }
        mainShip.update(delta);
        bullets.updateActiveObjects(delta);
        bullets.freeAllDestroyedObjects();
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).draw(batch);
        }
        mainShip.draw(batch);
        bullets.drawActiveObjects(batch);
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < stars.size() ; i++) {
            stars.get(i).resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        bullets.setWorldBounds(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        super.dispose();
        mainAtlas.dispose();
        bgTexture.dispose();

    }
}
