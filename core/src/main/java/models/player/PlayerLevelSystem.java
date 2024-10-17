package models.player;

public class PlayerLevelSystem {
    private int level;
    private int experience;
    private int experienceThreshold;
    private HealthComponent stats;

    public PlayerLevelSystem(HealthComponent stats) {
        this.level = 1;
        this.experience = 0;
        this.experienceThreshold = 100; // начальный порог
        this.stats = stats;
    }

    public void addExperience(int exp) {
        experience += exp;
        if (experience >= experienceThreshold) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        experience = 0; // сбрасываем опыт
        experienceThreshold *= 1.5; // увеличиваем порог уровня
        stats.setAttack(stats.getAttack() + 2); // увеличиваем атаку
        stats.setProtection(stats.getProtection() + 1); // увеличиваем защиту
        stats.setHealth(stats.getHealth() + 10); // увеличиваем здоровье
        // можно добавить другие бонусы за уровень
    }
    public int getLevel(){
        return  level;
    }
    public void dispose(){

    }
}
