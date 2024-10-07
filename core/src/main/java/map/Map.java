package map;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Map {
    private World world;

    public Map(World world) {
        this.world = world;
    }

    public void createRoom(float x, float y, float width, float height) {
        // Определяем тело
        // Создаем 4 стены
        createWall(x, y, width, 1f); // Нижняя стена
        createWall(x, y + height, width, 1f); // Верхняя стена
        createWall(x, y, 1f, height); // Левая стена
        createWall(x + width, y, 1f, height); // Правая стена
    }
    public void createWall(float x, float y, float width, float height) {
        // Определяем тело
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2); // Позиционируем по центру стены
        bodyDef.type = BodyDef.BodyType.StaticBody; // Статическое тело
        Body wallBody = world.createBody(bodyDef);

        // Определяем форму тела
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Половина ширины и высоты

        // Определяем фиксацию
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        wallBody.createFixture(fixtureDef);
        shape.dispose(); // Освобождаем форму после создания фиксации

        wallBody.setUserData("Wall");
    }
    public void createCorridor(float x, float y, float width, float height) {
        // Аналогично создаем коридор
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody; // Статическое тело
        Body corridorBody = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Половина ширины и высоты

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        corridorBody.createFixture(fixtureDef);
        shape.dispose(); // Освобождаем форму после создания фиксации
        corridorBody.setUserData("Corridor");


    }
}
