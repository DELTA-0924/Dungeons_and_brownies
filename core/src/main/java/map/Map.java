package map;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private World world;

    public Map(World world) {
        this.world = world;
    }

    public void createRoomWithCorridors(float x, float y, float width, float height, List<Rectangle> corridors) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body roomBody = world.createBody(bodyDef);

        List<Vector2> verticesList = new ArrayList<>();

        // Добавляем углы комнаты
        verticesList.add(new Vector2(-width / 2, -height / 2));  // Левый нижний
        verticesList.add(new Vector2(width / 2, -height / 2));   // Правый нижний
        verticesList.add(new Vector2(width / 2, height / 2));    // Правый верхний
        verticesList.add(new Vector2(-width / 2, height / 2));   // Левый верхний
        verticesList.add(verticesList.get(0)); // Замыкаем цепочку

        // Убираем сегменты стен, где находятся коридоры
        for (Rectangle corridor : corridors) {
            // Получаем координаты коридора
            float corridorLeft = corridor.x;
            float corridorRight = corridor.x + corridor.width;
            float corridorBottom = corridor.y;
            float corridorTop = corridor.y + corridor.height;

            // Удаляем нижнюю стену, если коридор пересекает ее
            if (corridorTop > -height / 2 && corridorBottom < -height / 2) {
                verticesList.remove(0); // Удаляем левый нижний угол
                verticesList.remove(1); // Удаляем правый нижний угол
                verticesList.add(0, new Vector2(corridorRight - width / 2, -height / 2)); // Новый нижний правый угол
                verticesList.add(1, new Vector2(corridorLeft - width / 2, -height / 2));  // Новый нижний левый угол
            }

            // Удаляем верхнюю стену, если коридор пересекает ее
            if (corridorBottom < height / 2 && corridorTop > height / 2) {
                verticesList.remove(2); // Удаляем левый верхний угол
                verticesList.remove(3); // Удаляем правый верхний угол
                verticesList.add(2,new Vector2(corridorLeft - width / 2, height / 2)); // Новый верхний левый угол
                verticesList.add(3,new Vector2(corridorRight - width / 2, height / 2)); // Новый верхний правый угол
            }

            // Удаляем левую стену, если коридор пересекает ее
            if (corridorRight > -width / 2 && corridorLeft < -width / 2) {
                verticesList.remove(0); // Удаляем левый нижний угол
                verticesList.remove(3); // Удаляем левый верхний угол
                verticesList.add(0, new Vector2(-width / 2, corridorTop - height / 2)); // Новый левый верхний угол
                verticesList.add(3, new Vector2(-width / 2, corridorBottom - height / 2)); // Новый левый нижний угол
            }

            // Удаляем правую стену, если коридор пересекает ее
            if (corridorLeft < width / 2 && corridorRight > width / 2) {
                verticesList.remove(1); // Удаляем правый нижний угол
                verticesList.remove(2); // Удаляем правый верхний угол
                verticesList.add(1,new Vector2(width / 2, corridorBottom - height / 2)); // Новый правый нижний угол
                verticesList.add(2,new Vector2(width / 2, corridorTop - height / 2)); // Новый правый верхний угол
            }
        }

        // Преобразуем список вершин в массив
        Vector2[] vertices = new Vector2[verticesList.size()];
        verticesList.toArray(vertices);

        // Создаем форму комнаты
        ChainShape roomShape = new ChainShape();
        roomShape.createChain(vertices);

        // Определяем фиксацию
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = roomShape;
        roomBody.createFixture(fixtureDef);

        roomShape.dispose(); // Освобождаем форму после создания фиксации
        roomBody.setUserData("Room");
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
