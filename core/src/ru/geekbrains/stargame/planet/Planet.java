package ru.geekbrains.stargame.planet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;



public class Planet {

    private Texture texture;
    private Vector2 position;
    private Vector2 velosity;
    private Vector2 sizeVector;
    private Vector2 finish;
    private float startX = 100;
    private float startY = 100;
    private float dt;

    public Planet (String texturePath) {
        texture = new Texture(texturePath);
        position = new Vector2(startX, startY);
        velosity = new Vector2();
        sizeVector = new Vector2(0, 0);
        finish = new Vector2();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        sizeVector.set(0, 0);
        finish.set(0, 0);
        sizeVector.add(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2));
        finish.add(sizeVector);
        sizeVector.sub(position);
    }

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void updatePosition() {
        if(!position.epsilonEquals(finish, 1f)) {
            velosity.add(sizeVector);
            velosity.scl(dt);
            position.add(velosity);
        }

    }
}
