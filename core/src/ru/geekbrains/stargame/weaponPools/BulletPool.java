package ru.geekbrains.stargame.weaponPools;

import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.weapon.Bullet;


public class BulletPool extends SpritesPool<Bullet> {


    @Override
    protected Bullet newObject() {
        return new Bullet();
    }



}
