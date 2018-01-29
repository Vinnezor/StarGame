package ru.geekbrains.stargame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.planet.Planet;

public class MenuScreen extends Base2DScreen{

    private SpriteBatch batch;
    private Texture bgTexture;
    private Planet planet;



    public MenuScreen(Game game) {
        super(game);
    }


    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        bgTexture = new Texture("bg.jpeg");
        planet = new Planet("planet/planet.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgTexture, 0, 0);
        update(delta);
        batch.end();
    }

    public void update(float delta) {
        planet.setDt(delta);
        planet.updatePosition();
        batch.draw(planet.getTexture(), planet.getPosition().x, planet.getPosition().y);

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        bgTexture.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        planet.setPosition(screenX, getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
