package ru.geekbrains.stargame.ships;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.math.Rnd;
import ru.geekbrains.stargame.engine.utils.Regions;

public class EnemyEmmiter {

    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final float ENEMY_SMALL_SHIP_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.25f;
    private static final int ENEMY_MIDDLE_BULLET_DAMAGE = 3;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 3f;
    private static final float ENEMY_MIDDLE_SHIP_HEIGHT = 0.15f;
    private static final int ENEMY_MIDDLE_HP = 5;

    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.06f;
    private static final float ENEMY_BIG_BULLET_VY = -0.15f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 4f;
    private static final float ENEMY_BIG_SHIP_HEIGHT = 0.2f;
    private static final int ENEMY_BIG_HP = 20;


    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMiddleRegions;
    private final TextureRegion[] enemyBigRegions;
    private Vector2 enemySmallVelocity = new Vector2(0f,  -0.2f);
    private Vector2 enemyMiddleVelocity = new Vector2(0f,  -0.1f);
    private Vector2 enemyBigVelocity = new Vector2(0f,  -0.05f);

    private float generateTimer;
    private float generateInterval;

    private EnemyShipPool enemyShipPool;
    private Rect worldBounds;
    private TextureRegion bulletRegion;

    private int stage;


    public EnemyEmmiter(TextureAtlas atlas, EnemyShipPool enemyShipPool, Rect worldBounds) {
        this.enemyShipPool = enemyShipPool;
        this.worldBounds = worldBounds;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMiddleRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
        this.generateInterval = 4f;
    }

    public void setToNewGame() {
        stage = 1;
    }

    public void generateEnemy(float delta, int frags) {
        stage = frags / 3 + 1;
        generateTimer += delta;
        if (generateInterval <= generateTimer) {
            generateTimer = 0;
            EnemyShip enemyShip = enemyShipPool.obtain();
            float probability = (float) Math.random();

            if(probability < 0.7f) smallShipSetProp(enemyShip);
            else if (probability < 0.9f) middleShipSetProp(enemyShip);
            else bigShipSetProp(enemyShip);

            enemyShip.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }

    private void smallShipSetProp(EnemyShip enemyShip) {
        enemyShip.setPropEnemyShip(
                enemySmallRegions,
                enemySmallVelocity,
                bulletRegion,
                ENEMY_SMALL_BULLET_HEIGHT,
                ENEMY_SMALL_BULLET_VY,
                ENEMY_SMALL_BULLET_DAMAGE * stage,
                ENEMY_SMALL_RELOAD_INTERVAL,
                ENEMY_SMALL_SHIP_HEIGHT,
                ENEMY_SMALL_HP + stage
        );
    }

    private void middleShipSetProp(EnemyShip enemyShip) {
        enemyShip.setPropEnemyShip(
                enemyMiddleRegions,
                enemyMiddleVelocity,
                bulletRegion,
                ENEMY_MIDDLE_BULLET_HEIGHT,
                ENEMY_MIDDLE_BULLET_VY,
                ENEMY_MIDDLE_BULLET_DAMAGE * stage,
                ENEMY_MIDDLE_RELOAD_INTERVAL,
                ENEMY_MIDDLE_SHIP_HEIGHT,
                ENEMY_MIDDLE_HP + stage
        );
    }

    private void bigShipSetProp(EnemyShip enemyShip) {
        enemyShip.setPropEnemyShip(
                enemyBigRegions,
                enemyBigVelocity,
                bulletRegion,
                ENEMY_BIG_BULLET_HEIGHT,
                ENEMY_BIG_BULLET_VY,
                ENEMY_BIG_BULLET_DAMAGE * stage,
                ENEMY_BIG_RELOAD_INTERVAL,
                ENEMY_BIG_SHIP_HEIGHT,
                ENEMY_BIG_HP + stage
        );
    }

    public int getStage() {
        return stage;
    }
}
