package Model;

/**
 * Class models an abstract Zombie to be implemented by other zombie classes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class AbstractZombie extends Sprite {

    private int speed;

    public AbstractZombie() {
        super();
        this.speed = 10;
    }

    public AbstractZombie(String imagePath, int health, int damage, int speed, int counter) {
        super(imagePath, health, damage,counter);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}