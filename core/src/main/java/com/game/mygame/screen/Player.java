package com.game.mygame.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    Texture texture;
    private float x,y;
    public float speed;
    private float width=75,height=75;
    private Body body;

    public Player(World world, Texture texture , float x, float y, float speed){
        this.texture=texture;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Динамическое тело
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
      // Половина ширины и высоты игрока

        // Определяем фиксацию
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Плотность
        fixtureDef.friction = 0.5f; // Трение
        fixtureDef.restitution = 0.2f; // Упругость

        body.createFixture(fixtureDef);
        shape.dispose();
    }
    public void render(SpriteBatch batch) {
        // Отрисовываем персонажа
        batch.draw(texture, x, y,width,height);
    }
    public Body getBody() {
        return body;
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
