package Model;

public class Peashooter extends AbstractPlant {

    public Peashooter()  {
        super("src/images/PEASHOOTER.png", 100, 35, 2,100);
    }

    public boolean canShoot() {
        if (this.getCounter() == 0) {
            this.setCounter(2);
            return true;
        }
        return false;
    }

    public Bullet shootBullet()  {
        return new Bullet(getDamage(), 1);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
