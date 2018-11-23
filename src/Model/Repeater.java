package Model;

public class Repeater extends AbstractPlant {

    //Same as Peashooter, except it shoots two bullets at once. Lowered bullet damage.

    public Repeater()  {
        super("src/images/REPEATER.png", 100, 20, 2,100);
    }

    /**
     * Clone constructor
     * @param repeater Repeater to be cloned
     */
    public Repeater(Repeater repeater){
        super(repeater);
    }

    public boolean canShoot() {
        if (this.getCounter() != 0) {
            this.decrementCounter();
            return true;
        }
        else {
            return false;
        }
    }

    public Bullet shootBullet()  {
        return new Bullet(getDamage(), 1);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}