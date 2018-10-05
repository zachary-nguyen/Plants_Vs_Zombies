package Model;

public class Peashooter extends AbstractPlant {

    public Peashooter() {
        super("P", 100, 20, 2,100);
    }


    public boolean canShoot() {
        if (this.getCounter() == 0) {
            this.setCounter(2);
            return true;
        }
        return false;
    }

    public Bullet shoot() {
        return new Bullet(10, 1);
    }
}
