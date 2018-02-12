package ru.geekbrains.stargame.weapon;

import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class BulletPool extends SpritesPool<Bullet> {


    @Override
    protected Bullet newObject() {
        return new Bullet();
    }



}
