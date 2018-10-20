package Model;

public class Bullet extends Sprite {

    private int speed;

    public Bullet(int damage, int speed) {
        super("O", 0, damage, 0);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
