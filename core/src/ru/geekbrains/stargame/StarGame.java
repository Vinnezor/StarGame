package ru.geekbrains.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture backgroung;
	private Sprite backgroundSprite;
	public static int WIDTH_WINDOW;
	public static int HEIGHT_WINDOW;
	private ImageDrive imageDrive;


	@Override
	public void create () {
		WIDTH_WINDOW = Gdx.graphics.getWidth();
		HEIGHT_WINDOW = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		backgroung = new Texture("stars4.jpeg");
		backgroundSprite = new Sprite(backgroung);
		backgroundSprite.setSize(WIDTH_WINDOW, HEIGHT_WINDOW);
		imageDrive = new ImageDrive();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		backgroundSprite.draw(batch);
		batch.draw(imageDrive.getRegion(), imageDrive.getPosition().x, imageDrive.getPosition().y);
		imageDrive.update(Gdx.graphics.getDeltaTime());
		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();
		backgroung.dispose();

	}


}
