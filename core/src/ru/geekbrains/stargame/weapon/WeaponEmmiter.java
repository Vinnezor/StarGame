package ru.geekbrains.stargame.weapon;


import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class WeaponEmmiter {

    public static final String WEAPON_BULLET = "bullets";
    public static final String WEAPON_LASER = "laser";

    private String weaponName;
    private BulletPool bulletPool;
    private Sound soundBullet;
    private Sound soundLaser;
    private float volume;
    private LaserPool laserPool;




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
        }
        else throw new RuntimeException("несуществующее оружие");

    }

    public void setWeaponBullet () {
        weaponName = WEAPON_BULLET;
    }

    public void setWeaponLaser() {
        weaponName = WEAPON_LASER;
    }

    public String getWeaponName() {
        return weaponName;
    }


    public void setSoundBullet(Sound soundBullet) {
        this.soundBullet = soundBullet;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setSoundLaser(Sound soundLaser) {
        this.soundLaser = soundLaser;
    }
}
