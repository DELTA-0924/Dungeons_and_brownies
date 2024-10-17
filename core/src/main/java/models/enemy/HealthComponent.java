package models.enemy;

public class HealthComponent {
    private int  health;
    private int protection;
    private int attack;
    private boolean dead;
    public HealthComponent(int health,int attack,int protection){
        this.attack=attack;
        this.health=health;
        this.protection=protection;

    }

    public void takeDamage(int damage){
        if (health > 0) {
            if(damage<=protection) {
                health -= 3; // Учитываем защиту

            }
            else {
                health -= Math.max(damage - protection, 0); // Учитываем защиту

            }
        }
            if (health <= 0) {
                dead = true;
            }
    }

    public void reset() {
        health = 10;
        protection = 5;
        attack = 5;
        dead = false;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    boolean isDead(){
        return dead;
    }
    public int getHealth(){
        return health;
    }
    public int getAttack(){
        return attack;
    }
    public int getProtection(){
        return protection;
    }
}
