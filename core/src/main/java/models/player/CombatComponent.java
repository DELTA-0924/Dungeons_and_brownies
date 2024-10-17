package models.player;

public class CombatComponent {
    private HealthComponent healthComponent;

    public CombatComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }

    public int takeDamage(int damage) {
        return healthComponent.takeDamage(damage);
    }
    public void dispose(){

    }
}
