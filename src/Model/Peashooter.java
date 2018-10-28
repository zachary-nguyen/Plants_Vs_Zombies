package Model;

/**
 * Peashooter class is a type of plant that has no special effects.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Peashooter extends AbstractPlant {

    public Peashooter() {
        super("P", 100, 50, 3,100);
    }

    public boolean canShoot() {
        if (this.getCounter() == 0) {
            this.setCounter(3);
            return true;
        }
        return false;
    }

    public Bullet shootBullet() {
        return new Bullet(getDamage(), 1);
    }
}
