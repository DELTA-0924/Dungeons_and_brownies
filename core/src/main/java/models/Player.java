package models;

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
    public Body body;

    public Player(World world, Texture texture , float x, float y, float speed){
        this.texture=texture;
        this.x=x;
        this.y=y;
        this.speed=speed;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((this.x+width)/2, (this.y+height)/2);
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Динамическое тело
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
      // Половина ширины и высоты игрока
        body.setBullet(true);
        // Определяем фиксацию
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Плотность
        fixtureDef.friction = 0.5f; // Трение
        fixtureDef.restitution = 0.2f; // Упругость

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData("Player");
    }
    public void render(SpriteBatch batch) {
        // Отрисовываем персонажа
        batch.draw(texture, body.getPosition().x-width/2, body.getPosition().y-height/2,width,height);
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
