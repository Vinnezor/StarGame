package ru.geekbrains.stargame.weapon;


import ru.geekbrains.stargame.engine.pool.SpritesPool;

public class WeaponEmmiter {

    private static final String WEAPON_BULLET = "bullets";
    private static final String WEAPON_LASER = "laser";

    private String weaponName;
    private BulletPool bulletPool;
    private LaserPool laserPool;


    public SpritesPool createNewWeaponPool () {
        if(weaponName.equals(WEAPON_LASER)){
            if(laserPool == null) {
                laserPool = new LaserPool();
            }
           return laserPool;
        }
        else if (weaponName.equals(WEAPON_BULLET)){
            if (bulletPool == null) {
                bulletPool = new BulletPool();
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

}
