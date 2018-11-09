package Model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Class models an abstract Zombie to be implemented by other zombie classes.
 * @author Zachary Nguyen, Eric Cosoreanu, Fareed Ahmad, Matthew Smith
 */
public abstract class AbstractZombie extends Sprite {

    private int speed;

    public AbstractZombie() throws IOException {
        super();
        this.speed = 10;
    }

    public AbstractZombie(Image img, int health, int damage, int speed, int counter) {
        super(img, health, damage,counter);
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}