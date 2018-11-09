package Model;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Bullet class is shot by plants to kill zombies
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public class Bullet extends Sprite {
    private int speed;

    public Bullet(int damage, int speed) throws IOException {

        super(ImageIO.read(new File("src/images/BULLET.png")), 0, damage, speed);
        this.speed = speed;
    }

    /***********************
     * GETTERS and SETTERS
     ***********************/

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() { return speed; }
}
