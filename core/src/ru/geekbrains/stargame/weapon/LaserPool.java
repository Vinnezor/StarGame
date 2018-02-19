package ru.geekbrains.stargame.weapon;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.pool.SpritesPool;


public class LaserPool extends SpritesPool<Laser> {

    private Sound sound;
    private float volume;

    @Override
    protected Laser newObject() {
        Laser laser = new Laser();
        laser.setSound(sound, volume);
        return laser;
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
