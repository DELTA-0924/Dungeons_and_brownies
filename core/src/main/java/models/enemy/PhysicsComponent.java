package models.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsComponent {
    private Body body;
    private Vector2 spawnPos;
    public PhysicsComponent(World world,float x,float y,float width,float height){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Динамическое тело для движущегося врага
        bodyDef.position.set(x, y); // Задаем начальную позицию врага
        body = world.createBody(bodyDef);
        spawnPos=new Vector2(x,y);
        // Определение формы хитбокса (прямоугольник)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Устанавливаем размеры хитбокса

        // Определение фикстуры
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10.0f; // Плотность (можно настроить под нужды)
        fixtureDef.friction = 0.3f; // Трение (насколько враг "скользит" по поверхности)
        fixtureDef.restitution = 0.0f; // Упругость (насколько сильно враг отскакивает при ударе)
        body.createFixture(fixtureDef);
        // Очистка ресурсов формы после использования
        shape.dispose();
    }
    public Body getBody(){
        return body;
    }
}
