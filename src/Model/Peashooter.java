package Model;

public class Peashooter extends AbstractPlant {

    public Peashooter() {
        super("P", 100, 20, 0,100);
    }


    public boolean canShoot() {
        this.setCounter(this.getCounter() + 1);
        if (this.getCounter() == 2) {
            this.setCounter(0);
            return true;
        }
        return false;
    }

    public Bullet shoot() {
        return new Bullet(10, 1);
    }
}
