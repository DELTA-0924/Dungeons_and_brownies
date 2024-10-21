package models.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.mygame.screen.common.DamageText;

public class Player {
    private float x, y;
    private int speed = 1000;
    private float width = 75, height = 75;
    private Boolean dead = false;

    private HealthComponent healthComponent;
    private PhysicsComponent physicsComponent;
    private CombatComponent combatComponent;
    private RenderComponent renderComponent;
    private PlayerLevelSystem expsys;
    private Stage stage;
    public Player(World world, Texture texture, float x, float y,Stage stage) {
        this.x = x;
        this.y = y;
        this.stage=stage;
        this.healthComponent = new HealthComponent(10, 5, 5); // Здоровье, защита, атака
        this.physicsComponent = new PhysicsComponent(world, x, y, width, height);
        this.combatComponent = new CombatComponent(healthComponent);
        this.renderComponent = new RenderComponent(texture, width, height);

        // Передаем пользовательские данные в тело Box2D
        this.physicsComponent.getBody().setUserData(this);

        this.expsys=new PlayerLevelSystem(this.getStats());
    }

    public void takeDamage(int damage) {
        DamageText damageText = new DamageText( combatComponent.takeDamage(damage)
                                                , physicsComponent.getBody().getPosition().x - width / 2,
                                                physicsComponent.getBody().getPosition().y - height / 2); // Передаем координаты игрока
        stage.addActor(damageText);


        if (healthComponent.isDead()) {
            dead = true;
        }

    }

    public void render(SpriteBatch batch) {
        renderComponent.render(batch, physicsComponent.getBody());
    }

    public void reset() {
        healthComponent.reset(); // Сброс статов
    }
    public boolean isDead(){
        return  dead;
    }
    public Body getBody() {
        return physicsComponent.getBody();
    }
    public Rectangle getBoundingRectangle() {
        // Получаем позицию тела Box2D
        float x = getBody().getPosition().x - width / 2; // Центрируем прямоугольник
        float y = getBody().getPosition().y - height / 2;

        return new Rectangle(x, y, width/2, height/2);
    }
    public void  expUp(int expValue){
        expsys.addExperience(expValue);
    }
    public int getSpeed(){
        return speed;
    }
    public HealthComponent getStats(){
        return this.healthComponent;
    }
    public int  getLvl(){
        return expsys.getLevel();
    }
    public void dispose(){
        healthComponent.dispose();
        combatComponent.dispose();
        physicsComponent.dispose();
        renderComponent.dispose();
        expsys.dispose();
    }
    public void setStats(HealthComponent stats)
    {
        healthComponent.setHealth(stats.getHealth());
        healthComponent.setProtection(stats.getProtection());
        healthComponent.setAttack(stats.getAttack());
    }

}
