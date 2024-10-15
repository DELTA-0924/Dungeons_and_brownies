package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mygame.screen.GameOverScreen;
import com.game.mygame.screen.common.CategoryBits;
import com.game.mygame.screen.common.DamageText;

public class Player {
    Texture texture;
    private float x,y;
    public float speed=1000;
    private float width=75,height=75;
    public Body body;
    private int  health=10,protection=5,attack=5;
    public Boolean dead=false;
    Stage stage;
    public Player(World world, Texture texture , float x, float y , Stage stage){
        this.texture=texture;
        this.x=x;
        this.y=y;
        this.stage=stage;
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((this.x+width)/2, (this.y+height)/2);
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Динамическое тело
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);
      // Половина ширины и высоты игрока
        // Определяем фиксацию
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10.0f; // Плотность
        fixtureDef.friction = 0.5f; // Трение
        fixtureDef.restitution = 0.0f; // Упругость
        fixtureDef.filter.categoryBits = CategoryBits.PLAYER; // Категория для игрока
        fixtureDef.filter.maskBits = CategoryBits.WALL | CategoryBits.HOLE; // Игрок может взаимодействовать со стенами и дырками
        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }
    public void takeDamage(int damage){

        if(health!=0) {
            health -= damage;
            DamageText damageText = new DamageText(damage, this.getX(), this.getY()); // Передаем координаты игрока
            stage.addActor(damageText);
        }
        else
            dead=true;


    }

    private float getX() {
        return body.getPosition().x-width/2;
    }

    private float getY() {
        return body.getPosition().y-height/2;
    }

    public int getHealth(){
        return this.health;
    }
    public int getAttack(){
        return this.attack;
    }
    public int getProtection(){
        return this.protection;
    }
    public Body getBody(){
        return body;
    }
    public Rectangle getBoundingRectangle() {
        // Получаем позицию тела Box2D
        float x = body.getPosition().x - width / 2; // Центрируем прямоугольник
        float y = body.getPosition().y - height / 2;

        return new Rectangle(x, y, width/2, height/2);
    }
    public void render(SpriteBatch batch) {
        // Отрисовываем персонажа

        batch.draw(texture, body.getPosition().x-width/2, body.getPosition().y-height/2,width,height);

    }

    public void dispose() {
        texture.dispose();
    }
    public void reset(){
        health=10;
        protection=5;
        attack=5;
    }

}
