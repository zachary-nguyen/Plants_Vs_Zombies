package Model;

/**
 * Plants vs Zombies
 * Sprite Class used to model all entities in the game
 */
public abstract class Sprite{

	private String name;
	private int health;
	private int damage;

    public Sprite() {
        name = null;
        health = 0;
        damage = 0;
    }

	public Sprite(String name, int health, int damage) {
		this.name = name;
		this.health = health;
		this.damage = damage;
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

    public String toString() {
        return getName();
    }

}