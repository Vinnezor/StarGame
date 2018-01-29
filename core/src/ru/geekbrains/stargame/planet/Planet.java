package ru.geekbrains.stargame.planet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;



public class Planet {

    private Texture texture;
    private Vector2 position;
    private Vector2 velosity;
    private float startX;
    private float startY;

    public Planet (String texturePath) {
        texture = new Texture(texturePath);
        position = new Vector2(startX, startY);
        velosity = new Vector2(50, 50);
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
        position.x = x - (texture.getWidth() / 2);
        position.y = y - (texture.getHeight() / 2);
        System.out.println(position.x +" "+ position.y);
    }

    public Vector2 getVelosity() {
        return velosity;
    }

    public void setVelosity(Vector2 velosity) {
        this.velosity = velosity;
    }


}
