package ru.geekbrains.stargame.weapon;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class BulletPool extends SpritesPool<Bullet> {

    private Sound sound;
    private float volume;


    @Override
    protected Bullet newObject() {
        Bullet bullet = new Bullet();
        bullet.setSound(sound, volume);
        return bullet;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
