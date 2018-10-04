package Model;

/**
 *
 */
public abstract class AbstractZombie extends Sprite {

    private int speed;

    public AbstractZombie() {
        super();
        this.speed = 10;
    }

    public AbstractZombie(String name, int health, int damage, int speed,int counter) {
        super(name, health, damage,counter);
        this.speed = speed;
    }
}