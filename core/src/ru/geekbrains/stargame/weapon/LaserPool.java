package ru.geekbrains.stargame.weapon;

import ru.geekbrains.stargame.engine.pool.SpritesPool;


public class LaserPool extends SpritesPool<Laser> {

    @Override
    protected Laser newObject() {
        return new Laser();
    }
}
