/**
 * Plants vs Zombies
 * Sprite Class used to model all entities in the game
 */
public abstract class Sprite{

	private String name;
	private int health;
	private int damage;
	
	public Sprite(String name, int health, int damage) {
		this.name = name;
		this.health = health;
		this.damage = damage;
	}
	
	
	public void getName() {
		return name;
	}
	
	public void getHealth() {
		return health;
	}
	
	public void getDamage() {
		return damage;
	}
	
	public String toString() {
		return getName();
	}
	
}