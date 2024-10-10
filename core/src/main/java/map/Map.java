package map;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.mygame.screen.Roque;
import com.game.mygame.screen.common.CategoryBits;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Map {
    private World world;
    Texture wallTexture;
    Roque game;
    Vector<Rectangle> halls;
    public Map(World world,Roque game) {
        this.game=game;
        wallTexture = new Texture("images/texture/stoneground.png");
        this.world = world;
    }


    public void createRoom(float x, float y, float width, float height,Vector<Rectangle> halls) {

        this.halls=halls;

        createWall(x, y, width, 1f); // Нижняя стена
        createWall(x, y + height, width, 1f); // Верхняя стена
        createWall(x, y, 1f, height); // Левая стена
        createWall(x + width, y, 1f, height); // Правая стена
        for (Rectangle hole : halls) {
//            createWallCorridors(hole.x,hole.width,hole.y,1f);
//            createWallCorridors(hole.x,y+hole.height,hole.width,1f);
//            createWallCorridors(hole.x,hole.y,1f,hole.height);
//            createWallCorridors(hole.x+hole.width,hole.y,1f,hole.height);

            createWallCorridors(hole.x,hole.y,hole.width,hole.height);

        }
    }
    public void createWallCorridors(float x,float y,float width,float height){
        // Создаём дырки в стене

            BodyDef holeBodyDef = new BodyDef();
            holeBodyDef.position.set( x+width  / 2, y + height / 2);
            Body holeBody = world.createBody(holeBodyDef);

            // Создаём форму дырки
            PolygonShape holeShape = new PolygonShape();
            holeShape.setAsBox(width / 2, height / 2);

            FixtureDef holeFixtureDef = new FixtureDef();
            holeFixtureDef.shape = holeShape;
            holeFixtureDef.isSensor = true; // Дырка является сенсором
            holeFixtureDef.filter.categoryBits = CategoryBits.HOLE; // Категория для дырок
            holeFixtureDef.filter.maskBits = CategoryBits.WALL | CategoryBits.PLAYER;
            holeBody.createFixture(holeFixtureDef);
            // Освобождаем память
            holeShape.dispose();
    }
    public void createWall(float x, float y, float width, float height) {
        // Создаём основное тело стены
        if(x==0&& y==0 && width==0 && height==0)
            return;
        System.out.println("x = "+x+" y="+y+" width= "+width+" height +"+height);
        BodyDef wallBodyDef = new BodyDef();
        wallBodyDef.position.set(x + width / 2, y + height / 2);
        Body wallBody = world.createBody(wallBodyDef);

        // Создаём форму стены
        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(width / 2, height / 2);

        FixtureDef wallFixtureDef = new FixtureDef();
        wallFixtureDef.shape = wallShape;
        wallFixtureDef.isSensor = false; // Обычная стена
        wallFixtureDef.filter.categoryBits = CategoryBits.WALL;
        wallBody.createFixture(wallFixtureDef);

        // Освобождаем память
        wallShape.dispose();

    }

}
