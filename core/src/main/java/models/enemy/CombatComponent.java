package models.enemy;

import com.badlogic.gdx.utils.TimeUtils;

import models.enemy.HealthComponent;
import models.player.Player;

public class CombatComponent {
    HealthComponent  healthComponent;
    private static final long attackCooldown = 1000;
    private long lastAttackTime = 0;
    public CombatComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }

    public void takeDamage(int damage) {
        healthComponent.takeDamage(damage);
    }

    public void dispose(){

    }
    public void attackPlayer(Player player) {
        // Проверяем, прошёл ли кулдаун
        if (TimeUtils.timeSinceMillis(lastAttackTime) > attackCooldown) {
            player.takeDamage(healthComponent.getAttack()); // Наносим урон игроку
            lastAttackTime = TimeUtils.millis(); // Обновляем время последней атаки
        }
    }
}
