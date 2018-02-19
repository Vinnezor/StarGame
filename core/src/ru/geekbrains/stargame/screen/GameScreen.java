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
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import java.util.List;
import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.engine.ActionListener;
import ru.geekbrains.stargame.engine.Base2DScreen;
import ru.geekbrains.stargame.engine.font.Font;
import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.explosion.ExplosionPool;
import ru.geekbrains.stargame.ships.EnemyEmmiter;
import ru.geekbrains.stargame.ships.EnemyShip;
import ru.geekbrains.stargame.ships.EnemyShipPool;
import ru.geekbrains.stargame.ships.MainShip;
import ru.geekbrains.stargame.star.TrackingStar;
import ru.geekbrains.stargame.ui.ButtonNewGame;
import ru.geekbrains.stargame.ui.MessageGameOver;
import ru.geekbrains.stargame.weapon.Bullet;
import ru.geekbrains.stargame.weapon.BulletPool;


public class GameScreen extends Base2DScreen implements ActionListener {



    private enum State {PLAYING, GAMEOVER};
    private State state;

    private final int COUNT_STARS_ON_SCREEN = 20;
    public final static float VOLUME = 0.1f;
    private static final float FONT_SIZE = 0.04f;
    private final BulletPool bullets = new BulletPool();
    private ExplosionPool explosions;
    private Texture bgTexture;
    private TextureAtlas mainAtlas;
    private Background background;
    private MainShip mainShip;
    private EnemyShipPool enemyShipPool;
    private EnemyEmmiter enemyEmmiter;
    private ArrayList<TrackingStar> stars;
    private Sound soundExplosion;
    private Sound mainShipShootSounds;
    private Sound enemyShipShootSounds;
    private Music gameScreenMusic;
    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;
    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbStage;

    private int frags;




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
        gameScreenMusic.setVolume(VOLUME);
        gameScreenMusic.setLooping(true);
        gameScreenMusic.play();

        //текстуры
        bgTexture = new Texture("textures/gameBG.png");
        mainAtlas = new TextureAtlas("textures/mainAtlas.tpack");

        //Оостальные преобразования
        background = new Background(new TextureRegion(bgTexture));
        explosions = new ExplosionPool(mainAtlas, soundExplosion);
        mainShip = new MainShip(mainAtlas, bullets,  explosions, worldBounds, mainShipShootSounds);
        enemyShipPool = new EnemyShipPool(bullets, explosions, worldBounds, mainShip, enemyShipShootSounds);
        enemyEmmiter = new EnemyEmmiter(mainAtlas, enemyShipPool, worldBounds);
        stars = new ArrayList<TrackingStar>(COUNT_STARS_ON_SCREEN);
        for (int i = 0; i < COUNT_STARS_ON_SCREEN ; i++) {
            stars.add(new TrackingStar(mainAtlas, Rnd.nextFloat(-0.05f, 0.05f), Rnd.nextFloat(-0.5f, -0.1f), 0.01f, mainShip.getVelocity()));
        }
        messageGameOver = new MessageGameOver(mainAtlas);
        buttonNewGame = new ButtonNewGame(mainAtlas, this);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbStage = new StringBuilder();
        font = new Font("font/font.fnt", "font/font.png");
        font.setWordSize(FONT_SIZE);
        startNewGame();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(state == State.PLAYING) checkCollisions();
        deleteAllDestroyed();
        draw();
        update(delta);
    }

    public void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbStage.setLength(0);
        font.draw(batch, sbFrags.append("Frags: ").append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append("HP: ").append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbStage.append("Stage: ").append(enemyEmmiter.getStage()), worldBounds.getRight(), worldBounds.getTop(), Align.right);

    }

    public void deleteAllDestroyed(){
        enemyShipPool.freeAllDestroyedObjects();
        bullets.freeAllDestroyedObjects();
        explosions.freeAllDestroyedObjects();
        enemyShipPool.freeAllDestroyedObjects();
    }



    public void update(float delta) {
        for (int i = 0; i < stars.size(); i++) {
            stars.get(i).update(delta);
        }

        explosions.updateActiveObjects(delta);
        switch (state) {
            case PLAYING:
                mainShip.update(delta);
                enemyShipPool.updateActiveObjects(delta);
                enemyEmmiter.generateEnemy(delta, frags);
                bullets.updateActiveObjects(delta);
                if (mainShip.isDestroyed()) state = State.GAMEOVER;
                break;
            case GAMEOVER:

                break;
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
        explosions.drawActiveObjects(batch);
        printInfo();
        if(state == State.GAMEOVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        } else {
            enemyShipPool.drawActiveObjects(batch);
            bullets.drawActiveObjects(batch);
            mainShip.draw(batch);
        }
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

    private void startNewGame() {
        state = State.PLAYING;
        enemyEmmiter.setToNewGame();
        frags = 0;
        mainShip.setToNewGame();
        enemyShipPool.freeAllActiveObjects();
        bullets.freeAllActiveObjects();
        //explosions.freeAllActiveObjects();
    }

    public void checkCollisions() {

        //столкновение кораблей
        List<EnemyShip> enemyShipsList = enemyShipPool.getActiveObjects();
        EnemyShip enemyShip;
        float minDist;
        for (int i = 0; i < enemyShipsList.size(); i++) {
             enemyShip = enemyShipsList.get(i);
            if(enemyShip.isDestroyed()) continue;
            minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if(enemyShip.pos.dst2(mainShip.pos) < minDist * minDist){
                enemyShip.damage(enemyShip.getHp());
                mainShip.damage(mainShip.getHp());
                return;
            }
        }


        //нанесение урона вражескому кораблю
        List<Bullet> bulletsList = bullets.getActiveObjects();
        Bullet bullet;
        for (int i = 0; i < enemyShipsList.size(); i++) {
            enemyShip = enemyShipsList.get(i);
            if (enemyShip.isDestroyed()) continue;

            for (int j = 0; j < bulletsList.size(); j++) {
                bullet = bulletsList.get(j);
                if(bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if(enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                    if(enemyShip.isDestroyed()) {
                        frags++;
                        break;
                    }
                }
            }
        }

        // нанесение урона игровому кораблю
        for (int i = 0; i < bulletsList.size(); i++) {
            bullet = bulletsList.get(i);
            if(bullet.getOwner() == mainShip || bullet.isDestroyed()) continue;
            if(mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.setDestroyed(true);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) mainShip.keyUp(keycode);
        return false;
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        if(state == State.PLAYING) mainShip.touchDown(touch, pointer);
        else buttonNewGame.touchDown(touch, pointer);

    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        if(state == State.PLAYING) mainShip.touchUp(touch, pointer);
        else buttonNewGame.touchUp(touch, pointer);

    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonNewGame) {
            startNewGame();
        } else {
            throw  new RuntimeException("Неизветная кнопка в src");
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        mainAtlas.dispose();
        bgTexture.dispose();
        bullets.dispose();
        explosions.dispose();
        enemyShipPool.dispose();
        soundExplosion.dispose();
        mainShipShootSounds.dispose();
        enemyShipShootSounds.dispose();
        font.dispose();


    }
}
