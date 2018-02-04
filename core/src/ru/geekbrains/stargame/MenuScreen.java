package ru.geekbrains.stargame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.ActionListener;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.planet.Planet;
import ru.geekbrains.stargame.ui.ButtonExit;
import ru.geekbrains.stargame.ui.ButtonPlay;

public class MenuScreen extends Base2DScreen implements ActionListener {

    private final float BUTTON_HEIGHT = 0.15f;
    private final float BUTTON_WIDTH = 0.15f;
    private final float BUTTON_PRESS_SCALE = 0.9f;

    private Texture bgTexture;
    private Texture btnPlayTexture;
    private Planet planet;
    private TextureAtlas atlas;

    private Background background;
    private ButtonPlay btnPlay;
    private ButtonExit btnExit;

    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.jpeg");
        atlas = new TextureAtlas("menuAtlas.tpack");
        btnPlayTexture = new Texture("play4.png");
//        planet = new Planet("planet/planet.png");
        background = new Background(new TextureRegion(bgTexture));
        btnPlay = new ButtonPlay(new TextureRegion(btnPlayTexture));
        btnExit = new ButtonExit(atlas, BUTTON_PRESS_SCALE, this);
        btnExit.setHeightProportion(BUTTON_HEIGHT);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        btnExit.draw(batch);
        //batch.draw(bgTexture,-0.5f, -0.5f, 1f, 1f);
        //batch.draw(planet.getTexture(), -1f, -1f, 2f, 2f);
        //update(delta);
        batch.end();
    }

    public void update(float delta) {
//        planet.setDt(delta);
//        planet.updatePosition();
//        batch.draw(planet.getTexture(), planet.getPosition().x, planet.getPosition().y);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
        btnExit.touchUp(touch, pointer);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        btnExit.touchDown(touch, pointer);
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        btnExit.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        btnPlayTexture.dispose();
       // planet.getTexture().dispose();
        bgTexture.dispose();
    }


    @Override
    public void actionPerformed(Object src) {
        if(src == btnExit) Gdx.app.exit();
        else throw new RuntimeException("неизвестная кнопка");
    }
}
