package ru.geekbrains.stargame.weapon;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.pool.SpritesPool;
import ru.geekbrains.stargame.screen.GameScreen;

public class WeaponEmmiter {

    public static final String WEAPON_BULLET = "bullets";
    public static final String WEAPON_LASER = "laser";
    public static final String WEAPON_ROCKET = "rocket";

    private String weaponName;
    private BulletPool bulletPool;
    private Sound soundBullet;
    private Sound soundLaser;
    private float volume = GameScreen.VOLUME;
    private LaserPool laserPool;
    private RocketPool rocketPool;




    public SpritesPool changeWeaponPool () {
        if(weaponName.equals(WEAPON_LASER)){
            if(laserPool == null) {
                laserPool = new LaserPool();
                laserPool.setSound(soundLaser);
                laserPool.setVolume(volume);
            }
           return laserPool;
        }
        else if (weaponName.equals(WEAPON_BULLET)){
            if (bulletPool == null) {
                bulletPool = new BulletPool();
                bulletPool.setSound(soundBullet);
                bulletPool.setVolume(volume);
            }
            return bulletPool;
        }else if (weaponName.equals(WEAPON_ROCKET)) {
            if(rocketPool == null) {
                rocketPool = new RocketPool();
                rocketPool.setSound(soundBullet);
                rocketPool.setVolume(volume);
            }
            return rocketPool;
        }
        else throw new RuntimeException("несуществующее оружие");
    }

    public void rndWeaponChange() {
        float rnd = (float) Math.random();
        if (rnd < 0.4f) setWeaponBullet();
        else if ( rnd < 0.6f) setWeaponLaser();
        else if ( rnd < 0.8f) setWeaponRocket();
        changeWeaponPool();
    }

    public void setWeaponBullet () {
        weaponName = WEAPON_BULLET;
    }

    public void setWeaponLaser() {
        weaponName = WEAPON_LASER;
    }

    public void setWeaponRocket() {weaponName = WEAPON_ROCKET;}

    public String getWeaponName() {
        return weaponName;
    }

    public void setSoundBullet(Sound soundBullet) {
        this.soundBullet = soundBullet;
    }

    public void setSoundLaser(Sound soundLaser) {
        this.soundLaser = soundLaser;
    }
}
