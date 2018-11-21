package Model;

/**
 * Bullet class is shot by plants to kill zombies
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Bullet extends Sprite {
    private int speed;
    private boolean move;

    public Bullet(int damage, int speed){

        super("src/images/BULLET.png", 0, damage, speed);
        this.speed = speed;
        move = false;
    }

    public Bullet(Bullet bullet){
        super(bullet);
        this.move = bullet.move;
        this.speed = bullet.speed;
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() { return speed; }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    @Override
    public int compareTo(Object o) {
        return -1;
    }
}
