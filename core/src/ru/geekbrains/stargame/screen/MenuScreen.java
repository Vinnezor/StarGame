package ru.geekbrains.stargame.screen;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.engine.ActionListener;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.font.Font;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.star.Star;
import ru.geekbrains.stargame.ui.ButtonExit;
import ru.geekbrains.stargame.ui.ButtonPlay;

public class MenuScreen extends Base2DScreen implements ActionListener {

    private static final float FONT_SIZE = 0.07f;
    private final float BUTTON_HEIGHT = 0.15f;
    private final float BUTTON_WIDTH = 0.15f;
    private final float BUTTON_PRESS_SCALE = 0.9f;


    private Texture bgTexture;
    private TextureAtlas atlas;
    private final int COUNT_STARS_ON_SCREEN = 20;

    private Background background;
    private ButtonPlay btnPlay;
    private ButtonExit btnExit;


    private Font font;

    private ArrayList<Star> stars;
    private boolean showRecord;

    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("textures/bg.jpeg");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
//        planet = new Planet("planet/planet.png");
        background = new Background(new TextureRegion(bgTexture));
        btnPlay = new ButtonPlay(atlas, BUTTON_PRESS_SCALE, this);
        btnPlay.setHeightProportion(BUTTON_HEIGHT);
        btnExit = new ButtonExit(atlas, BUTTON_PRESS_SCALE, this);
        btnExit.setHeightProportion(BUTTON_HEIGHT);

        stars = new ArrayList<Star>(COUNT_STARS_ON_SCREEN);
        for (int i = 0; i < COUNT_STARS_ON_SCREEN ; i++) {
            stars.add(new Star(atlas, Rnd.nextFloat(-0.05f, 0.05f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f));
        }

        font = new ru.geekbrains.stargame.engine.font.Font("font/font.fnt", "font/font.png");
        font.setWordSize(FONT_SIZE);
        showRecord = false;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).draw(batch);
        }
        btnPlay.draw(batch);
        btnExit.draw(batch);
        batch.end();
    }


    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        btnExit.touchUp(touch, pointer);
        btnPlay.touchUp(touch, pointer);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        btnExit.touchDown(touch, pointer);
        btnPlay.touchDown(touch, pointer);
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i = 0; i < stars.size() ; i++) {
          stars.get(i).resize(worldBounds);
        }
        btnExit.resize(worldBounds);
        btnPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bgTexture.dispose();

    }


    @Override
    public void actionPerformed(Object src) {
        if(src == btnExit) Gdx.app.exit();
        else if(src == btnPlay) {
            game.setScreen(new GameScreen(game));
        }
        else throw new RuntimeException("неизвестная кнопка");
    }
}
