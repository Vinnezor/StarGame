package ru.geekbrains.stargame.weaponPools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.engine.math.Rect;
import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.weapon.MainBullet;


public class MainBulletPool extends SpritesPool<MainBullet> {
    private TextureAtlas atlas;
    private Rect worldBounds;


    public MainBulletPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected MainBullet newObject() {
        MainBullet bullet = new MainBullet(atlas);
        bullet.resize(worldBounds);
        return bullet;
    }

    public int getCountFreeBullet () {
        return freeObjects.size();
    }

    public void setWorldBounds(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }
}
