package ru.geekbrains.stargame.explosion;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.engine.Sprite;
import ru.geekbrains.stargame.screen.GameScreen;

public class Explosion  extends Sprite {

    private float animateInterval = 0.017f; //время между кадрами анимации
    private float animateTimer;
    private Sound sound;

    public Explosion(TextureRegion region, int rows, int cols, int frame, Sound sound) {
        super(region, rows, cols, frame);
        this.sound = sound;
    }

    public void setExplosionSize(float height, Vector2 pos){
        this.pos.set(pos);
        setHeightProportion(height);
        sound.setVolume(sound.play(), GameScreen.VOLUME);
    }


    public void destroy() {
        frame = 0;
        setDestroyed(true);
    }

    @Override
    public void update(float dt) {
        animateTimer += dt;
        if (animateTimer >= animateInterval) {
            animateTimer = 0;
            if(++frame == regions.length){
                destroy();
            }
        }
    }
}
