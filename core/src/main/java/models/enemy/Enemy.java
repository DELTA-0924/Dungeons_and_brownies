package models.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;


import models.player.Player;

public class Enemy {
    public EnemyUI enemyUI;
    private boolean isAggressive = false;
    private Rectangle currentRoom;
    private Player player;
    private int speed=500;
    private HealthComponent healthComponent;
    private CombatComponent combatComponent;
    private RenderComponent renderComponent;
    private PhysicsComponent physicsComponent;
    private EnemyLevelSystem expsys;
    private  float width=75,height=75;
    private float posX,posY;
    private Vector2 spawnPos;
    private boolean dead=false;

    public Enemy(World world, Texture texture, Rectangle startRoom, Player player, Stage stage){
        currentRoom=startRoom;
        posX=currentRoom.x+currentRoom.width/2;
        posY=currentRoom.y+currentRoom.height/2;
        spawnPos=new Vector2(posX,posY);
        physicsComponent=new PhysicsComponent(world,posX,posY,width, height);
        healthComponent=new HealthComponent(10,5,5);
        combatComponent=new CombatComponent(healthComponent);
        renderComponent=new RenderComponent(texture,width,height);
        expsys=new EnemyLevelSystem(getStats());
        physicsComponent.getBody().setUserData(this);
        enemyUI=new EnemyUI(stage,healthComponent);
        enemyUI.uiTable.setVisible(false);
        this.player=player;
    }
    public void render(SpriteBatch batch) {
        renderComponent.render(batch, physicsComponent.getBody());
    }

    public void update() {
        if(player.getLvl()>this.getLvl())
            expsys.levelUp();
        if (isPlayerInRoom()) {
            // Если игрок в комнате, делаем врага агрессивным
            enemyUI.uiTable.setVisible(true);
            enemyUI.update();
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
    public void attackPlayer(){
        combatComponent.attackPlayer(player);
    }
    public void takeDamage(int damage){

        combatComponent.takeDamage(damage);
        if(healthComponent.isDead())
            die();
    }
    private boolean isPlayerInRoom() {

        // Проверка, находится ли игрок в той же комнате
        return currentRoom.overlaps(player.getBoundingRectangle());
    }

    private void followPlayer() {
        // Логика преследования игрока
        Vector2 direction = new Vector2(
            player.getBody().getPosition().x - physicsComponent.getBody().getPosition().x,
            player.getBody().getPosition().y - physicsComponent.getBody().getPosition().y
        );
        direction.nor(); // Нормализуем направление
        physicsComponent.getBody().setLinearVelocity(direction.scl(speed)); // Двигаем врага к игроку с определённой скоростью
    }

    private void returnToSpawn() {
        // Логика возврата на исходную позицию
        Vector2 direction = new Vector2(
            spawnPos.x - physicsComponent.getBody().getPosition().x,
            spawnPos.y -  physicsComponent.getBody().getPosition().y
        );
        // Если враг еще не на исходной позиции, движемся к ней
        if (direction.len() >1) {

            direction.nor(); // Нормализуем направление
            physicsComponent.getBody().setLinearVelocity(direction.scl(speed)); // Двигаем врага к точке спавна
        } else {
            // Останавливаем движение, если враг почти на месте
            physicsComponent.getBody().setLinearVelocity(0, 0);
        }
        enemyUI.uiTable.setVisible(false);
    }
    private void die() {
        // Логика смерти врага
        dead=true;
        player.expUp(100);
        System.out.println("Enemy died!");

    }
    public HealthComponent getStats(){
        return healthComponent;
    }
    public int getLvl(){
        return expsys.getLevel();
    }
    public boolean isDead(){
        return  dead;
    }
    public void dispose(){
        renderComponent.dispose();
    }
    public void dispose(World world){
        world.destroyBody(physicsComponent.getBody());
    }

}
