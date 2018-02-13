package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.explosion.Explosion;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.ships.EnemyShip;
import ru.geekbrains.stargame.ships.MainShip;
import ru.geekbrains.stargame.star.TrackingStar;
import ru.geekbrains.stargame.weapon.BulletPool;


public class GameScreen extends Base2DScreen {

    private final int COUNT_STARS_ON_SCREEN = 20;
    private final BulletPool bullets = new BulletPool();

    private ExplosionPool explosions;
    private Texture bgTexture;
    private TextureAtlas mainAtlas;
    private Background background;
    private MainShip mainShip;
    private EnemyShip enemyShip;
    private ArrayList<TrackingStar> stars;
    private Sound soundExplosion;
    private Sound mainShipShootSounds;
    private Sound enemyShipShootSounds;
    private Music gameScreenMusic;



    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        //музыка и звуки
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        mainShipShootSounds = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyShipShootSounds = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        gameScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        gameScreenMusic.setLooping(true);
        gameScreenMusic.play();

        //текстуры
        bgTexture = new Texture("textures/gameBG.png");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");

        //Оостальные преобразования
        background = new Background(new TextureRegion(bgTexture));
        mainShip = new MainShip(mainAtlas, bullets, mainShipShootSounds);
        enemyShip = new EnemyShip();
        enemyShip.setPropEnemyShip(mainAtlas, "enemy0", bullets, enemyShipShootSounds);
        stars = new ArrayList<TrackingStar>(COUNT_STARS_ON_SCREEN);
        for (int i = 0; i < COUNT_STARS_ON_SCREEN ; i++) {
            stars.add(new TrackingStar(mainAtlas, Rnd.nextFloat(-0.05f, 0.05f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f, mainShip.getVelocity()));
        }

        explosions = new ExplosionPool(mainAtlas, soundExplosion);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        deleteAllDestroyed();
        draw();
        update(delta);
    }

    public void deleteAllDestroyed(){
        bullets.freeAllDestroyedObjects();
        explosions.freeAllDestroyedObjects();
    }


    public void update(float delta) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).update(delta);
        }
        mainShip.update(delta);
        bullets.updateActiveObjects(delta);
        explosions.updateActiveObjects(delta);
        enemyShip.update(delta);
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
        explosions.drawActiveObjects(batch);
        enemyShip.draw(batch);
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
        enemyShip.resize(worldBounds);
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
        Explosion explosion = explosions.obtain();
        explosion.setExplosionSize(0.1f, touch);
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
        bullets.dispose();
        explosions.dispose();
        soundExplosion.dispose();
        mainShipShootSounds.dispose();

    }
}
