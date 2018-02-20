package ru.geekbrains.stargame.weapon;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class RocketPool extends SpritesPool<Rocket> {

    private Sound sound;
    private float volume;

    @Override
    protected Rocket newObject() {
        Rocket rocket = new Rocket();
        rocket.setSound(sound, volume);
        return rocket;
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
