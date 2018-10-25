package Model;

public class Bullet extends Sprite {
    private int speed;

    public Bullet(int damage, int speed) {

        super(">", 0, damage, speed);
        this.speed = speed;
    }

    public int getSpeed() { return speed; }
}
