package models.player;

public class HealthComponent {
    private int health;
    private int protection;
    private int attack;
    private boolean dead = false;

    public HealthComponent(int health, int protection, int attack) {
        this.health = health;
        this.protection = protection;
        this.attack = attack;
    }

    public int takeDamage(int damage) {
        int criticaldamage=0;
        if (health > 0) {
            if(damage<=protection) {
                health -= 3; // Учитываем защиту
                criticaldamage=3;
            }
            else {
                health -= Math.max(damage - protection, 0); // Учитываем защиту
                criticaldamage=damage - protection;
            }
        }
        if (health <= 0) {
            dead = true;
        }
        return criticaldamage;
    }

    public boolean isDead() {
        return dead;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getProtection() {
        return protection;
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

    public void reset() {
        health = 10;
        protection = 5;
        attack = 5;
        dead = false;
    }
    public void dispose(){

    }
}
