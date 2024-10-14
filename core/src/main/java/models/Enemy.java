package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.TimeUtils;

public class Enemy {
    private Texture character;
    private int attack;
    private int protection;
    private int speed;
    private int health;
    public float x,y;
    private World world;
    private Body body;
    private boolean isAggressive = false;
    private Player player; // Ссылка на игрока
    private Rectangle currentRoom; // Комната, в которой находится враг
    private float width=75,height=75,posX,posY;
    private Vector2 spawnPos;
    // Время последней атаки
    private long lastAttackTime = 0;
    // Кулдаун в миллисекундах (например, 1000 миллисекунд = 1 секунда)
    private static final long attackCooldown = 1000;
    public Enemy(World world, Player player, int health, int attack, int speed, int protection, Rectangle room,Texture character){
        currentRoom=room;
        this.attack=attack;
        this.speed=speed;
        this.protection=protection;
        this.health=health;
        this.world=world;
        this.character=character;
        this.player=player;
        posX=currentRoom.x+currentRoom.width/2;
        posY=currentRoom.y+currentRoom.height/2;
        createEnemyBody(posX,posY);
    }
    public void update() {
        if (isPlayerInRoom()) {
            // Если игрок в комнате, делаем врага агрессивным

            isAggressive = true;
        }
        else{
            isAggressive = false;

        }
        if (isAggressive) {
            // Враг следует за игроком
            followPlayer();

        }else{
            returnToSpawn();
        }
    }

    private boolean isPlayerInRoom() {

        // Проверка, находится ли игрок в той же комнате
        return currentRoom.overlaps(player.getBoundingRectangle());
    }

    private void followPlayer() {
        // Логика преследования игрока
        Vector2 direction = new Vector2(
            player.getBody().getPosition().x - body.getPosition().x,
            player.getBody().getPosition().y - body.getPosition().y
        );
        direction.nor(); // Нормализуем направление
        body.setLinearVelocity(direction.scl(speed)); // Двигаем врага к игроку с определённой скоростью
    }

    private void returnToSpawn() {
        // Логика возврата на исходную позицию
        Vector2 direction = new Vector2(
            spawnPos.x - body.getPosition().x,
            spawnPos.y - body.getPosition().y
        );
        // Если враг еще не на исходной позиции, движемся к ней
        if (direction.len() >1) {

            direction.nor(); // Нормализуем направление
            body.setLinearVelocity(direction.scl(speed)); // Двигаем врага к точке спавна
        } else {
            // Останавливаем движение, если враг почти на месте
            body.setLinearVelocity(0, 0);
        }
    }
    private void createEnemyBody(float x, float y) {
        // Определение тела врага
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
        body.setUserData(this);
        // Очистка ресурсов формы после использования
        shape.dispose();
    }
    public  void render(SpriteBatch batch){
        batch.draw(character,body.getPosition().x-width/2, body.getPosition().y-height/2,width,height);
    }
    public void attackPlayer() {
        // Проверяем, прошёл ли кулдаун
        if (TimeUtils.timeSinceMillis(lastAttackTime) > attackCooldown) {
            player.takeDamage(attack); // Наносим урон игроку
            lastAttackTime = TimeUtils.millis(); // Обновляем время последней атаки
        }
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die(); // Враг умирает
        }
    }

    private void die() {
        // Логика смерти врага
        System.out.println("Enemy died!");
    }
    int getProtection(){
        return protection;
    }
    int getHealth(){
        return health;
    }
    int getAttack(){
        return attack;
    }


}
