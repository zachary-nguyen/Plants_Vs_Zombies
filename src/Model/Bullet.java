package Model;

/**
 * Bullet class is shot by plants to kill zombies
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Bullet extends Sprite {
    private int speed;

    public Bullet(int damage, int speed) {

        super(">", 0, damage, speed);
        this.speed = speed;
    }

    public int getSpeed() { return speed; }
}
