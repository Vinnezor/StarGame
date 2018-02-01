package ru.geekbrains.stargame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.planet.Planet;

public class MenuScreen extends Base2DScreen{

    private Texture bgTexture;
    private Planet planet;

    private Background background;

    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        bgTexture = new Texture("bg.jpeg");
        planet = new Planet("planet/planet.png");
        background = new Background(new TextureRegion(bgTexture));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
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
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();

        planet.getTexture().dispose();
        bgTexture.dispose();
    }


}
