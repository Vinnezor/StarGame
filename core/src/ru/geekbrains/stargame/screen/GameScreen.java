package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.ships.MainShip;
import ru.geekbrains.stargame.star.Star;


public class GameScreen extends Base2DScreen {

    private final int COUNT_STARS_ON_SCREEN = 10;
    private final float SHIPS_HEIGHT = 0.15f;

    private Texture bgTexture;
    private TextureAtlas starsAtlas;
    private TextureAtlas mainAtlas;
    private Background background;

    private MainShip mainShip;
    private ArrayList<Star> stars;



    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("gameBG.png");
        background = new Background(new TextureRegion(bgTexture));
        starsAtlas = new TextureAtlas("menuAtlas.tpack");
        mainAtlas = new TextureAtlas("mainAtlas.tpack");
        stars = new ArrayList<Star>(COUNT_STARS_ON_SCREEN);
        mainShip = new MainShip(mainAtlas, SHIPS_HEIGHT);
        for (int i = 0; i < COUNT_STARS_ON_SCREEN ; i++) {
            stars.add(new Star(starsAtlas, Rnd.nextFloat(-0.05f, 0.05f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f));
        }

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
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == 21) {
            mainShip.moveLeft();
            return true;// left 21
        }
        if(keycode == 22) {
            mainShip.moveRight();
            return true;// right 22
        }
        if(keycode == 19) {
            mainShip.moveUp();
            return true; //up 19
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.moveStop();
        return false;
    }

    @Override
    public void dispose() {

        super.dispose();
        starsAtlas.dispose();
        bgTexture.dispose();
    }
}
