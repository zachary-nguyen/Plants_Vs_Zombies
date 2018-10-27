package Model;

/**
 * Plants vs Zombies
 * Sprite Class used to model all entities in the game
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class Sprite {

    private String name;
    private int health;
    private int damage;
    private int counter;

    public Sprite() {
        name = null;
        health = 0;
        damage = 0;
        counter = 0;
    }

    public Sprite(String name, int health, int damage, int counter) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * Decrements the counter of the sprite. If the sprite is a Sun then keep the counter such as.
     *
     */
    public void decrementCounter(){
        this.setCounter(this.getCounter() - 1);
    }

    @Override
    public String toString() {
        return getName();
    }

}