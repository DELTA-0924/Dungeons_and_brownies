package com.game.mygame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    Texture texture;
    private float x,y;
    private float speed;
    private float width=64,height=64;
    public Player(Texture texture ,float x,float y,float speed){
        this.texture=texture;
        this.x=x;
        this.y=y;
        this.speed=speed;
    }
    public void update(float delta) {
        // Обновляем позицию персонажа на основе ввода

    }

    public void render(SpriteBatch batch) {
        // Отрисовываем персонажа
        batch.draw(texture, x, y,75,75);
    }

    public void move(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }
    public  float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getWidth(){
        return width;
    }
    public float getHeight(){
        return height;
    }
    public float getSpeed(){
        return speed;
    }

    public void dispose() {
        texture.dispose();
    }
}
